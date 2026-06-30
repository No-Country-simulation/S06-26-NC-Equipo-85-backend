package com.appbit.orientation.dto;

import java.util.List;
import java.util.UUID;

/**
 * Representa un curso recomendado para ayudar al usuario
 * a reducir la brecha de habilidades detectada.
 */
public record SuggestedCourse(
        UUID courseId,
        String title,
        String provider,
        List<String> skillsContribuidos
) {
}
