package com.appbit.orientation.dto;

import java.util.UUID;

/**
 * Representa una habilidad faltante identificada durante el análisis
 * del perfil del usuario.
 *
 * <p>Estas habilidades forman parte de la brecha técnica (gap)
 * que el usuario debe cubrir para mejorar su compatibilidad
 * con determinadas vacantes.</p>
 */
public record GapItem(UUID id, String name, String level) {
}
