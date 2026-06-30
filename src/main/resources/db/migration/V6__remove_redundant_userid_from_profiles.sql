-- 1. Eliminar la columna redundante user_id
ALTER TABLE profiles DROP COLUMN IF EXISTS user_id;

-- Se elimina cualquier constraint de FK previo sobre esa columna 
ALTER TABLE profiles DROP CONSTRAINT IF EXISTS fk_profiles_user;

-- Añadimos la relación de Clave Primaria Compartida (Shared Primary Key)
ALTER TABLE profiles
    ADD CONSTRAINT fk_profiles_user
    FOREIGN KEY (id) 
    REFERENCES app_users(id)
    ON DELETE CASCADE;