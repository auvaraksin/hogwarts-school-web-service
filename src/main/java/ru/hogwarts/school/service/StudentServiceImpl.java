package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.interfaces.GetLastFiveStudents;
import ru.hogwarts.school.interfaces.StudentService;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
       return studentRepository.save(student);
    }

    public Collection<Student> getStudentAll() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).get();
    }

    public Collection<Student> getStudentsByAge(Integer age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getStudentByAgeBetween(Integer minAge, Integer maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> getStudentByFaculty(Long facultyId) {
        return studentRepository.findByFacultyId(facultyId);
    }

    public Faculty getStudentFaculty(Long studentId) {
        return studentRepository.findById(studentId).get().getFaculty();
    }

    public Integer getStudentsQuantity() {
        return studentRepository.getStudentsQuantity();
    }

    public Long getStudentsAverageAge() {
        return studentRepository.getStudentsAverageAge();
    }

    public List<GetLastFiveStudents> getLastFiveStudentsList() {
        return studentRepository.getLastFiveStudents();
    }

    public Student updateStudent(Student student) {
        if (studentRepository.existsById(student.getId()) != true) {
            return null;
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }
}
