package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.interfaces.GetLastFiveStudents;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(Integer age);

    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    Collection<Student> findByFacultyId(Long facultyId);

    @Query(value = "SELECT COUNT(id) FROM student", nativeQuery = true)
    Integer getStudentsQuantity();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Long getStudentsAverageAge();

    @Query(value = "SELECT id, name, age FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<GetLastFiveStudents> getLastFiveStudents();

}
