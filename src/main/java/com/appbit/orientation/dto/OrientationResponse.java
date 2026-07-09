package com.appbit.orientation.dto;

import java.util.List;

/**
 * Representa el resultado generado por el motor de matching.
 *
 * <p>Incluye la brecha de habilidades detectada, los cursos
 * recomendados, las vacantes compatibles y el nivel de confianza
 * del análisis realizado.</p>
 *
 * <p>Ejemplo:</p>
 *
 * <pre>
 * {
 *   "gapPorcentual": 35.5,
 *   "confianza": 92.0
 * }
 * </pre>
 */
public record OrientationResponse(
        Double gapPorcentual,
        List<GapItem> gapItems,
        List<SuggestedCourse> trayectoriaSugerida,
        List<JobMatch> vacantesCompatibles,
        Double confianza,
        String aiRecommendation
) {
}
