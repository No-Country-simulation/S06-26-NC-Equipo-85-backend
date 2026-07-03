INSERT INTO course_skills (course_id, skill_id)
SELECT c.id, s.id
FROM course c, skill s
WHERE
    (c.name='Java Fundamentals' AND s.name='Java')
   OR
    (c.name='Spring Boot Fundamentals' AND s.name='Spring Boot')
   OR
    (c.name='PostgreSQL Essentials' AND s.name='PostgreSQL')
   OR
    (c.name='Docker Basics' AND s.name='Docker')
   OR
    (c.name='Angular Fundamentals' AND s.name IN ('Angular','TypeScript'))
   OR
    (c.name='Flutter Fundamentals' AND s.name IN ('Flutter','Dart'));