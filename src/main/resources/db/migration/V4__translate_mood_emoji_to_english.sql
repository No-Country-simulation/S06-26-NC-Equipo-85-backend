-- 1. Traducir los emojis de estado de ánimo a inglés

--Desvincular temporalmente la columna en la tabla mod_checkins
ALTER TABLE mod_checkins DROP COLUMN IF EXISTS emoji;

--Eliminar el tipo ENUM viejo en español
DROP TYPE IF EXISTS mood_emoji;

--Crear el nuevo tipo ENUM en inglés
CREATE TYPE mood_emoji AS ENUM ('HAPPY', 'DEPRESSED', 'FURIOUS', 'ANXIOUS', 'NEUTRAL');

--Volvemos a agregar la columna emoji con el nuevo tipo ENUM en inglés
ALTER TABLE mod_checkins ADD COLUMN emoji mood_emoji;