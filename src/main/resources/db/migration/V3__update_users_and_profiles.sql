-- 1. ENUMs personalizados para roles, género, estado de ánimo y categorías de habilidades

CREATE TYPE user_role AS ENUM ('MENTOR', 'MENTEE');
CREATE TYPE gender_type AS ENUM ('MASCULINE', 'FEMININE', 'OTHER');
CREATE TYPE mood_emoji AS ENUM ('FELIZ', 'DEPRIMIDO', 'FURIOSO', 'ANSIOSO', 'NEUTRAL');
CREATE TYPE skill_category AS ENUM ('BACKEND', 'FRONTEND', 'MOBILE', 'DATA_SCIENCE', 'DESIGN_UX_UI', 'SOFT_SKILLS');

-- Enums para experiencias y cursos 
CREATE TYPE session_status AS ENUM ('PENDING', 'SCHEDULED', 'COMPLETED', 'CANCELED');
CREATE TYPE experience_type AS ENUM ('WORKSHOP', 'BOOTCAMP', 'WEBINAR', 'JOB_EXPERIENCE');
CREATE TYPE level_type AS ENUM ('BEGINNER', 'INTERMEDIATE', 'ADVANCED'); 
CREATE TYPE education_level_type AS ENUM (
    'HIGH_SCHOOL', 
    'TECHNICAL', 
    'UNDERGRADUATE', 
    'POSTGRADUATE', 
    'SELF_TAUGHT'
);

-- 2. Modificacion de tablas existentes: app_users y profiles

-- Actualizar app_users con el ENUM de roles
ALTER TABLE app_users 
    ADD COLUMN IF NOT EXISTS role user_role, -- ENUM de rol de usuario
    DROP COLUMN IF EXISTS updated_at;

-- Actualizar profiles con el ENUM de género
ALTER TABLE profiles 
    DROP COLUMN IF EXISTS first_name,
    DROP COLUMN IF EXISTS last_name;

ALTER TABLE profiles
    ADD COLUMN IF NOT EXISTS full_name VARCHAR(255),
    ADD COLUMN IF NOT EXISTS birth_date DATE,
    ADD COLUMN IF NOT EXISTS gender gender_type, -- ENUM de género
    ADD COLUMN IF NOT EXISTS education_level education_level_type, -- Nivel educativo
    ADD COLUMN IF NOT EXISTS continent VARCHAR(100),
    ADD COLUMN IF NOT EXISTS state VARCHAR(100),
    ADD COLUMN IF NOT EXISTS country VARCHAR(100),
    ADD COLUMN IF NOT EXISTS city VARCHAR(100),
    ADD COLUMN IF NOT EXISTS latitude FLOAT,
    ADD COLUMN IF NOT EXISTS longitude FLOAT,
    ADD COLUMN IF NOT EXISTS whatsapp VARCHAR(20),
    ADD COLUMN IF NOT EXISTS professional_level level_type, -- Enum de nivel profesional 
    ADD COLUMN IF NOT EXISTS tech_area VARCHAR(100),
    ADD COLUMN IF NOT EXISTS objective TEXT;

-- Modificamos experience para usar experience_type
ALTER TABLE experience 
    ALTER COLUMN type TYPE experience_type USING (type::experience_type);

-- 3.   Creacion de nuevas tablas para habilidades, experiencias, vacantes y cursos

-- Tabla intermedia Profile_skills
CREATE TABLE IF NOT EXISTS profile_skills (
    id SERIAL PRIMARY KEY,
    profile_id UUID NOT NULL,
    skill_id INTEGER NOT NULL,
    CONSTRAINT fk_ps_profile FOREIGN KEY (profile_id) REFERENCES profiles (id) ON DELETE CASCADE,
    CONSTRAINT fk_ps_skill FOREIGN KEY (skill_id) REFERENCES skill (id) ON DELETE CASCADE
);

-- Tabla Mod_checkins (Estado de Ánimo) con ENUM y restricción de Rating
CREATE TABLE IF NOT EXISTS mod_checkins (
    id SERIAL PRIMARY KEY,
    profile_id UUID NOT NULL,
    emoji mood_emoji, -- ENUM de estado de animo
    rating INTEGER CHECK (rating >= 1 AND rating <= 5), -- Rating asociado del 1 al 5
    context TEXT,
    suggested_action TEXT,
    derive_cvv BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    CONSTRAINT fk_mod_profile FOREIGN KEY (profile_id) REFERENCES profiles (id) ON DELETE CASCADE
);

-- Tabla Mentorship_sessions con ENUM de estatus
CREATE TABLE IF NOT EXISTS mentorship_sessions (
    id SERIAL PRIMARY KEY,
    mentor_profile_id UUID NOT NULL,
    mentee_profile_id UUID NOT NULL,
    schedule_date TIMESTAMP WITH TIME ZONE NOT NULL,
    status session_status DEFAULT 'PENDING', -- ENUM de estatus de la sesión
    is_practice_invitation BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_mentor FOREIGN KEY (mentor_profile_id) REFERENCES profiles (id),
    CONSTRAINT fk_mentee FOREIGN KEY (mentee_profile_id) REFERENCES profiles (id)
);