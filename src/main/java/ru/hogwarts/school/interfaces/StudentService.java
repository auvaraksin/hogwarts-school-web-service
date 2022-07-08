package ru.hogwarts.school.interfaces;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Collection<Student> getStudentAll();

    Student getStudentById(Long studentId);

    Collection<String> getStudentsBySubstring(String substring);

    Collection<Student> getStudentsByAge(Integer age);

    Collection<Student> getStudentByAgeBetween(Integer minAge, Integer maxAge);

    Collection<Student> getStudentByFaculty(Long facultyId);

    Faculty getStudentFaculty(Long studentId);

    Integer getStudentsQuantity();

    Double getStudentsAverageAge();

    Double getStudentsAverageAgeByStreamMethod();

    List<GetLastFiveStudents> getLastFiveStudentsList();

    Student updateStudent(Student student);

    void deleteStudent(Long studentId);
}
