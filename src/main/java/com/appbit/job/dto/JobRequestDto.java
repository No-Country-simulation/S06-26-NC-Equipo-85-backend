package com.appbit.job.dto;

import lombok.Data;

/**
 * DTO utilizado para recibir solicitudes de creación y actualización de ofertas laborales.
 *
 * <p>Contiene la información necesaria proporcionada por el cliente para crear
 * o modificar una oferta laboral.</p>
 *
 * * <p><b>Ejemplo de solicitud:</b></p>
 *  *
 *  * <pre>
 *  * {
 *  *   "company": "OpenAI",
 *  *   "title": "Backend Developer",
 *  *   "description": "Desarrollo de APIs REST con Spring Boot."
 *  * }
 *  * </pre>
 */

@Data
public class JobRequestDto {
    private String company;
    private String title;
    private String description;
}
