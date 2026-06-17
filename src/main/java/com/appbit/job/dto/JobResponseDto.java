package com.appbit.job.dto;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * DTO utilizado para devolver información de ofertas laborales a los consumidores de la API.
 *
 * <p>Representa la vista pública de una oferta laboral sin exponer detalles
 * internos de persistencia.</p>
 *
 * <p><b>Ejemplo de respuesta:</b></p>
 *
 * <pre>
 * {
 *   "id": "550e8400-e29b-41d4-XXXX-XXXXXXXXXXXX",
 *   "company": "OpenAI",
 *   "title": "Backend Developer",
 *   "description": "Desarrollo de APIs REST con Spring Boot."
 * }
 * </pre>
 */

@Data
@Builder
public class JobResponseDto {
    private UUID id;
    private String company;
    private String title;
    private String description;
}
