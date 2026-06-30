package com.appbit.health.service;

import com.appbit.common.enums.MoodEmoji;
import com.appbit.health.dto.*;
import com.appbit.health.model.ModCheckin;
import com.appbit.health.repository.ModCheckinRepository;
import com.appbit.profile.model.Profile;
import com.appbit.profile.repository.ProfileRepository;
import com.appbit.user.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HealthService {

    private static final String CHECKIN_MESSAGE =
            "Hemos recibido tu check-in. Tu bienestar es nuestra prioridad.";

    private static final String ALERT_ACTION =
            "Te recomendamos hablar con alguien de confianza o contactar a un profesional de salud mental.";

    private static final String DEFAULT_ACTION =
            "Continúa con tus actividades habituales y recuerda cuidar tu bienestar diario.";

    // Non-final: @Value uses field injection, excluded from @RequiredArgsConstructor
    @Value("${gemini.api-key}")
    private String geminiApiKey;

    private final WebClient geminiWebClient;
    private final ObjectMapper objectMapper;
    private final ModCheckinRepository checkinRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional
    public CheckinResponse saveCheckin(CheckinRequest request) {
        Profile profile = getAuthenticatedProfile();

        boolean isAlert = request.getRating() < 4;
        String suggestedAction = isAlert ? ALERT_ACTION : DEFAULT_ACTION;

        ModCheckin checkin = ModCheckin.builder()
                .profile(profile)
                .emoji(request.getEmoji())
                .rating(request.getRating())
                .context(request.getContext())
                .deriveCvv(isAlert)
                .suggestedAction(suggestedAction)
                .build();

        checkinRepository.save(checkin);

        return CheckinResponse.builder()
                .checkinId(checkin.getId())
                .mensaje(CHECKIN_MESSAGE)
                .accionSugerida(suggestedAction)
                .derivarCvv(isAlert)
                .notaActual(request.getRating())
                .alerta(isAlert)
                .build();
    }

    @Transactional(readOnly = true)
    public List<CheckinSummaryResponse> getCheckinHistory() {
        UUID profileId = getAuthenticatedProfile().getId();

        return checkinRepository.findByProfile_IdOrderByCreatedAtDesc(profileId)
                .stream()
                .map(c -> CheckinSummaryResponse.builder()
                        .id(c.getId())
                        .emoji(c.getEmoji())
                        .rating(c.getRating())
                        .context(c.getContext())
                        .suggestedAction(c.getSuggestedAction())
                        .deriveCvv(c.getDeriveCvv())
                        .createdAt(c.getCreatedAt())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public CheckinDetailResponse getCheckinDetail(UUID checkinId) {
        UUID profileId = getAuthenticatedProfile().getId();
        ModCheckin checkin = findCheckinWithOwnership(checkinId, profileId);

        return CheckinDetailResponse.builder()
                .id(checkin.getId())
                .emoji(checkin.getEmoji())
                .rating(checkin.getRating())
                .context(checkin.getContext())
                .suggestedAction(checkin.getSuggestedAction())
                .deriveCvv(checkin.getDeriveCvv())
                .createdAt(checkin.getCreatedAt())
                .build();
    }

    public String getEmpathicResponse(UUID checkinId) {
        UUID profileId = getAuthenticatedProfile().getId();
        ModCheckin checkin = findCheckinWithOwnership(checkinId, profileId);

        JsonNode geminiResponse = geminiWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1beta/models/gemini-2.5-flash:generateContent")
                        .queryParam("key", geminiApiKey)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(buildGeminiRequest(checkin.getEmoji(), checkin.getContext()))
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), response ->
                        response.bodyToMono(String.class)
                                .map(body -> new ResponseStatusException(
                                        HttpStatus.BAD_GATEWAY, "Gemini API error: " + body)))
                .bodyToMono(JsonNode.class)
                .block();

        return extractTextFromResponse(geminiResponse);
    }

    private ModCheckin findCheckinWithOwnership(UUID checkinId, UUID profileId) {
        ModCheckin checkin = checkinRepository.findById(checkinId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Check-in not found with id: " + checkinId));

        if (!checkin.getProfile().getId().equals(profileId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Access denied to this check-in");
        }
        return checkin;
    }

    private Map<String, Object> buildGeminiRequest(MoodEmoji emoji, String userContext) {
        String systemText =
                "Eres un psicólogo y asesor de bienestar emocional altamente empático de la plataforma AppBit. " +
                "Tu objetivo no es únicamente reconocer la emoción del usuario, sino ayudarle a sentirse comprendido y ofrecerle un pequeño apoyo emocional inmediato. " +
                "Siempre responde en español utilizando un tono cálido, humano y cercano. " +
                "No uses listas, viñetas ni formato estructurado: solo texto continuo y humano. " +
                "La respuesta debe tener entre 4 y 6 oraciones breves pero útiles para la persona y minimamente 50 palabras" + 
                " y no debe incluir consejos médicos ni diagnósticos. " +
                "Primero valida la emoción del usuario sin juzgarla."+
                "Después demuestra que entendiste el contexto específico que compartió."+
                "Luego ofrece una sugerencia práctica, sencilla y realista para ayudarle a manejar cómo se siente (por ejemplo respirar, descansar, hablar con alguien, salir a caminar, escribir lo que siente o darse un momento para sí mismo). La sugerencia debe adaptarse al contexto."+
                "Finaliza con un mensaje esperanzador o de apoyo."+
                "Nunca diagnostiques enfermedades ni intuyas atencion médica.";

        String userText = String.format(
                "El usuario reportó que se siente %s. " +
                "Contexto adicional que compartió: \"%s\". " +
                "Por favor, bríndale una respuesta empática y personalizada basada en esto.",
                translateEmoji(emoji),
                userContext != null && !userContext.isBlank()
                        ? userContext
                        : "No compartió contexto adicional."
        );

        return Map.of(
                "system_instruction", Map.of(
                        "parts", List.of(Map.of("text", systemText))
                ),
                "contents", List.of(Map.of(
                        "role", "user",
                        "parts", List.of(Map.of("text", userText))
                )),
                "generationConfig", Map.of(
                        "temperature", 0.9,
                        "maxOutputTokens", 512
                )
        );
    }

    private String extractTextFromResponse(JsonNode root) {
        if (root == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Empty response from Gemini");
        }
        JsonNode candidates = root.path("candidates");
        if (!candidates.isArray() || candidates.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "No candidates in Gemini response");
        }
        JsonNode parts = candidates.get(0).path("content").path("parts");
        if (!parts.isArray() || parts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "No parts in Gemini response");
        }
        JsonNode textNode = parts.get(0).path("text");
        if (textNode.isMissingNode()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "No text field in Gemini response");
        }
        return textNode.asText();
    }

    private String translateEmoji(MoodEmoji emoji) {
        return switch (emoji) {
            case HAPPY     -> "muy bien (HAPPY 😊)";
            case DEPRESSED -> "muy triste o deprimido (DEPRESSED 😞)";
            case ANXIOUS   -> "ansioso o muy preocupado (ANXIOUS 😰)";
            case FURIOUS   -> "furioso o muy enojado (FURIOUS 😡)";
            case NEUTRAL   -> "neutral, sin emociones fuertes (NEUTRAL 😐)";
        };
    }

    private Profile getAuthenticatedProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .flatMap(user -> profileRepository.findById(user.getId()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Profile not found for the authenticated user"));
    }
}
