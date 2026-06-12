-- 1. Tabla Principal: Skill (Habilidades)
CREATE TABLE skill (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    category VARCHAR(100)
);

-- 2. Tabla Principal: Experience (Experiencias/Eventos)
CREATE TABLE experience (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    speaker_name VARCHAR(255),
    speaker_role VARCHAR(150),
    type VARCHAR(100),
    content_url VARCHAR(255),
    date_time TIMESTAMP WITH TIME ZONE
);

-- 3. Tabla Principal: Job (Vacantes)
CREATE TABLE job (
    id SERIAL PRIMARY KEY,
    company VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- 4. Tabla Principal: Course (Cursos)
CREATE TABLE course (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    provider VARCHAR(150),
    level VARCHAR(100),
    url VARCHAR(255)
);

-- 5. Intermedia: Experience_skills (Vacantes-Habilidades)
CREATE TABLE experience_skills (
    id SERIAL PRIMARY KEY,
    experience_id INTEGER NOT NULL,
    skill_id INTEGER NOT NULL,
    CONSTRAINT fk_exp_skills_experience FOREIGN KEY (experience_id) REFERENCES experience (id) ON DELETE CASCADE,
    CONSTRAINT fk_exp_skills_skill FOREIGN KEY (skill_id) REFERENCES skill (id) ON DELETE CASCADE
);

-- 6. Intermedia: Job_skills (Vacantes-Habilidades)
CREATE TABLE job_skills (
    id SERIAL PRIMARY KEY,
    job_id INTEGER NOT NULL,
    skill_id INTEGER NOT NULL,
    CONSTRAINT fk_job_skills_job FOREIGN KEY (job_id) REFERENCES job (id) ON DELETE CASCADE,
    CONSTRAINT fk_job_skills_skill FOREIGN KEY (skill_id) REFERENCES skill (id) ON DELETE CASCADE
);

-- 7. Intermedia: Course_skills (Cursos-Habilidades)
CREATE TABLE course_skills (
    id SERIAL PRIMARY KEY,
    course_id INTEGER NOT NULL,
    skill_id INTEGER NOT NULL,
    CONSTRAINT fk_course_skills_course FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE,
    CONSTRAINT fk_course_skills_skill FOREIGN KEY (skill_id) REFERENCES skill (id) ON DELETE CASCADE
);