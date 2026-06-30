package com.appbit.orientation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * Solicitud utilizada para iniciar el proceso de orientación profesional.
 *
 * <p>El motor de matching utilizará el identificador del usuario para
 * recuperar desde la base de datos su perfil, habilidades, experiencia
 * y demás información necesaria para generar recomendaciones.</p>
 *
 * <p>Ejemplo:</p>
 *
 * <pre>
 * {
 *   "userId": "550e8400-e29b-41d4-a716-446655440000"
 * }
 * </pre>
 */
public record OrientationRequest(
        @NotNull(message = "userId es obligatorio")
        UUID userId
) {
}
