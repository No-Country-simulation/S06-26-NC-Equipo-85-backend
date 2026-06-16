-- 1. LIMPIEZA DE LLAVES FORÁNEAS VIEJAS (V2 y V3)
-- Tablas intermedias de la V2
ALTER TABLE experience_skills DROP CONSTRAINT IF EXISTS fk_exp_skills_experience;
ALTER TABLE experience_skills DROP CONSTRAINT IF EXISTS fk_exp_skills_skill;

ALTER TABLE job_skills DROP CONSTRAINT IF EXISTS fk_job_skills_job;
ALTER TABLE job_skills DROP CONSTRAINT IF EXISTS fk_job_skills_skill;

ALTER TABLE course_skills DROP CONSTRAINT IF EXISTS fk_course_skills_course;
ALTER TABLE course_skills DROP CONSTRAINT IF EXISTS fk_course_skills_skill;

-- Tablas intermedias de la V3
ALTER TABLE profile_skills DROP CONSTRAINT IF EXISTS fk_ps_profile;
ALTER TABLE profile_skills DROP CONSTRAINT IF EXISTS fk_ps_skill;
ALTER TABLE mentorship_sessions DROP CONSTRAINT IF EXISTS fk_mentor;
ALTER TABLE mentorship_sessions DROP CONSTRAINT IF EXISTS fk_mentee;
ALTER TABLE mod_checkins DROP CONSTRAINT IF EXISTS fk_mod_profile;

-- 2. CONVERSIÓN DE LAS TABLAS PRINCIPALES (V2) A UUID

-- Tabla: skill
ALTER TABLE skill DROP COLUMN IF EXISTS id;
ALTER TABLE skill ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();

-- Tabla: experience
ALTER TABLE experience DROP COLUMN IF EXISTS id;
ALTER TABLE experience ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();

-- Tabla: job
ALTER TABLE job DROP COLUMN IF EXISTS id;
ALTER TABLE job ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();

-- Tabla: course
ALTER TABLE course DROP COLUMN IF EXISTS id;
ALTER TABLE course ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();

-- 3. CONVERSIÓN DE LAS TABLAS INTERMEDIAS (V2) A UUID

-- Tabla: experience_skills
ALTER TABLE experience_skills DROP COLUMN IF EXISTS id;
ALTER TABLE experience_skills DROP COLUMN IF EXISTS experience_id;
ALTER TABLE experience_skills DROP COLUMN IF EXISTS skill_id;
ALTER TABLE experience_skills ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();
ALTER TABLE experience_skills ADD COLUMN experience_id UUID NOT NULL;
ALTER TABLE experience_skills ADD COLUMN skill_id UUID NOT NULL;

-- Tabla: job_skills
ALTER TABLE job_skills DROP COLUMN IF EXISTS id;
ALTER TABLE job_skills DROP COLUMN IF EXISTS job_id;
ALTER TABLE job_skills DROP COLUMN IF EXISTS skill_id;
ALTER TABLE job_skills ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();
ALTER TABLE job_skills ADD COLUMN job_id UUID NOT NULL;
ALTER TABLE job_skills ADD COLUMN skill_id UUID NOT NULL;

-- Tabla: course_skills
ALTER TABLE course_skills DROP COLUMN IF EXISTS id;
ALTER TABLE course_skills DROP COLUMN IF EXISTS course_id;
ALTER TABLE course_skills DROP COLUMN IF EXISTS skill_id;
ALTER TABLE course_skills ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();
ALTER TABLE course_skills ADD COLUMN course_id UUID NOT NULL;
ALTER TABLE course_skills ADD COLUMN skill_id UUID NOT NULL;

-- 4. CONVERSIÓN DE LAS TABLAS INTERMEDIAS (V3) A UUID

-- Tabla: profile_skills
ALTER TABLE profile_skills DROP COLUMN IF EXISTS id;
ALTER TABLE profile_skills DROP COLUMN IF EXISTS skill_id;
ALTER TABLE profile_skills ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();
ALTER TABLE profile_skills ADD COLUMN skill_id UUID NOT NULL;

-- Tabla: mod_checkins
ALTER TABLE mod_checkins DROP COLUMN IF EXISTS id;
ALTER TABLE mod_checkins ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();

-- Tabla: mentorship_sessions
ALTER TABLE mentorship_sessions DROP COLUMN IF EXISTS id;
ALTER TABLE mentorship_sessions ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();

-- 5. RECONSTRUCCIÓN DE LLAVES FORÁNEAS (Relaciones UUID )

-- Llaves de experience_skills
ALTER TABLE experience_skills 
    ADD CONSTRAINT fk_exp_skills_experience FOREIGN KEY (experience_id) REFERENCES experience (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_exp_skills_skill FOREIGN KEY (skill_id) REFERENCES skill (id) ON DELETE CASCADE;

-- Llaves de job_skills
ALTER TABLE job_skills 
    ADD CONSTRAINT fk_job_skills_job FOREIGN KEY (job_id) REFERENCES job (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_job_skills_skill FOREIGN KEY (skill_id) REFERENCES skill (id) ON DELETE CASCADE;

-- Llaves de course_skills
ALTER TABLE course_skills 
    ADD CONSTRAINT fk_course_skills_course FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_course_skills_skill FOREIGN KEY (skill_id) REFERENCES skill (id) ON DELETE CASCADE;

-- Llaves de profile_skills (V3)
ALTER TABLE profile_skills 
    ADD CONSTRAINT fk_ps_profile FOREIGN KEY (profile_id) REFERENCES profiles (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_ps_skill FOREIGN KEY (skill_id) REFERENCES skill (id) ON DELETE CASCADE;

-- Llaves de mod_checkins (V3)
ALTER TABLE mod_checkins 
    ADD CONSTRAINT fk_mod_profile FOREIGN KEY (profile_id) REFERENCES profiles (id) ON DELETE CASCADE;

-- Llaves de mentorship_sessions (V3)
ALTER TABLE mentorship_sessions 
    ADD CONSTRAINT fk_mentor FOREIGN KEY (mentor_profile_id) REFERENCES profiles (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_mentee FOREIGN KEY (mentee_profile_id) REFERENCES profiles (id) ON DELETE CASCADE;