INSERT INTO job_skills (job_id, skill_id)
SELECT j.id, s.id
FROM job j, skill s
WHERE
    (j.title = 'Junior Backend Developer' AND s.name IN ('Java','Spring Boot','PostgreSQL','Git'))
   OR
    (j.title = 'Backend Java Developer' AND s.name IN ('Java','Spring Boot','Docker','PostgreSQL','Git'))
   OR
    (j.title = 'Frontend Angular Developer' AND s.name IN ('Angular','TypeScript','HTML','CSS','Git'))
   OR
    (j.title = 'Flutter Developer' AND s.name IN ('Flutter','Dart','Git'));