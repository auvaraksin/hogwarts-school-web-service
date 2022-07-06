ALTER TABLE student ADD CONSTRAINT age_constraint CHECK (age >= 16);

ALTER TABLE student ADD CONSTRAINT name_constraint UNIQUE (name);

ALTER TABLE student ALTER COLUMN name SET NOT NULL;

ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculty ADD CONSTRAINT name_color_faculty_constraint UNIQUE (name, color);

SELECT student.name, student.age, faculty.name FROM student
                                                        LEFT JOIN faculty ON student.faculty_id = faculty.id;

SELECT * FROM student
                  INNER JOIN avatar a on student.id = a.student_id;