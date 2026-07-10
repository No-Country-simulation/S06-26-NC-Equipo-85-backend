package com.appbit.gemini.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * Servicio encargado de comunicarse con la API de Gemini
 * para generar recomendaciones personalizadas basadas
 * en el resultado del motor de matching.
 */

@Service
@RequiredArgsConstructor
public class GeminiService {
    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient geminiWebClient;
    private final ObjectMapper objectMapper;

    public String generateRecommendation(String prompt) {
        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        System.out.println("Llamando a Gemini...");
        String response = geminiWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1beta/models/gemini-2.5-flash:generateContent")
                        .queryParam("key", apiKey)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Respuesta recibida: " + response);

        try {
            JsonNode root = objectMapper.readTree(response);

            return root
                    .get("candidates")
                    .get(0)
                    .get("content")
                    .get("parts")
                    .get(0)
                    .get("text")
                    .asText();

        } catch (Exception e) {
            e.printStackTrace();
            return "No fue posible generar una recomendación.";
        }
    }
}
