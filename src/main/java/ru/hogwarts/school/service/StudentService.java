package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
       return studentRepository.save(student);
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.getById(studentId);
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

    public List<Student> getStudentsByAge(Integer age) {
        return studentRepository.findByAge(age);
    }
}
