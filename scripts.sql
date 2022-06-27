SELECT * FROM student WHERE age > 17 AND age < 26;

SELECT name FROM student;

SELECT * FROM student WHERE name LIKE '%Г%';

SELECT * FROM student WHERE age < 21;

SELECT * FROM student ORDER BY age;

SELECT COUNT(id) FROM student;

SELECT AVG(age) FROM student;

SELECT id, name, age FROM student ORDER BY id DESC LIMIT 5