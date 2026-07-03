/**
 * Inserta el catálogo inicial de habilidades técnicas
 * utilizadas por el motor de matching.
 *
 * Estas habilidades serán compartidas por:
 * - Perfiles
 * - Vacantes
 * - Cursos
 */
INSERT INTO skill (id, name)
VALUES
    (gen_random_uuid(), 'Java'),
    (gen_random_uuid(), 'Spring Boot'),
    (gen_random_uuid(), 'PostgreSQL'),
    (gen_random_uuid(), 'Docker'),
    (gen_random_uuid(), 'Git');