package com.appbit.orientation.dto;

import java.util.UUID;

/**
 * Representa una vacante compatible con el perfil del usuario.
 *
 * <p>Incluye el porcentaje de compatibilidad calculado
 * por el motor de matching.</p>
 */
public record JobMatch(
        UUID jobId,
        String company,
        String title,
        Double matchRate
) {
}
