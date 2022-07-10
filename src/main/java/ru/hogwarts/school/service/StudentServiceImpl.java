package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.interfaces.GetLastFiveStudents;
import ru.hogwarts.school.interfaces.StudentService;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Method to create student was invoked ");
        return studentRepository.save(student);
    }

    public Collection<Student> getStudentAll() {
        logger.info("Method to find all students was invoked ");
        return studentRepository.findAll();
    }

    public Student getStudentById(Long studentId) {
        logger.info("Method to find student by Id was invoked ");
        return studentRepository.findById(studentId).get();
    }

    public Collection<Student> getStudentsByAge(Integer age) {
        logger.info("Method to find students by age was invoked ");
        return studentRepository.findByAge(age);
    }

    public Collection<String> getStudentsBySubstring(String substring) {
        logger.info("Method to find students by substring was invoked ");
        return studentRepository
                .findAll()
                .stream()
                .parallel()
                .filter(student -> student.getName().startsWith(substring))
                .map(student -> student.getName())
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    public Collection<Student> getStudentByAgeBetween(Integer minAge, Integer maxAge) {
        logger.info("Method to find students between setting up ages was invoked");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> getStudentByFaculty(Long facultyId) {
        logger.info("Method to find students by faculty's Id was invoked");
        return studentRepository.findByFacultyId(facultyId);
    }

    public Faculty getStudentFaculty(Long studentId) {
        logger.info("Method to find all students in the faculty was invoked");
        return studentRepository.findById(studentId).get().getFaculty();
    }

    public Integer getStudentsQuantity() {
        logger.info("Method to find out student's quantity was invoked");
        return studentRepository.getStudentsQuantity();
    }

    public Double getStudentsAverageAge() {
        logger.info("Method to find out student's average age was invoked");
        return studentRepository.getStudentsAverageAge();
    }

    public Double getStudentsAverageAgeByStreamMethod() {
        logger.info("Method to find out student's average age by stream method was invoked");
        return studentRepository
                .findAll()
                .stream()
                .parallel()
                .mapToInt(student -> student.getAge())
                .average()
                .getAsDouble();
    }

    public List<GetLastFiveStudents> getLastFiveStudentsList() {
        logger.info("Method to show up last five student in the database was invoked");
        return studentRepository.getLastFiveStudents();
    }

    public Student updateStudent(Student student) {
        if (studentRepository.existsById(student.getId()) != true) {
            logger.warn("There is no student with name = " + student.getName());
            return null;
        }
        logger.info("Method to update student's personal data was invoked");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        logger.info("Method to delete student was invoked");
        studentRepository.deleteById(studentId);
    }

    public void getStudentsListByThreadMethod() {
        logger.info("Method to get students list by thread method was invoked");
        List<String> students = getStudentsListByStreamMethod(studentRepository);
        int firstPoint = students.size() / 3;
        int secondPoint = firstPoint * 2;
        //main thread
        printStudentsNameToConsole(0, firstPoint, students);
        //first parallel thread
        new Thread(() -> {
            printStudentsNameToConsole(firstPoint, secondPoint, students);
        }).start();
        //second parallel thread
        new Thread(() -> {
            printStudentsNameToConsole(secondPoint, students.size(), students);
        }).start();
    }

    public void getStudentsListBySynchronizedThreadMethod() {
        logger.info("Method to get students list by synchronized thread method was invoked");
        List<String> students = getStudentsListByStreamMethod(studentRepository);
        int firstPoint = students.size() / 3;
        int secondPoint = firstPoint * 2;
        //main thread
        printStudentsNameToConsoleSynchronizedMethod(0, firstPoint, students);
        //first parallel thread
        new Thread(() -> {
            printStudentsNameToConsoleSynchronizedMethod(firstPoint, secondPoint, students);
        }).start();
        //second parallel thread
        new Thread(() -> {
            printStudentsNameToConsoleSynchronizedMethod(secondPoint, students.size(), students);
        }).start();
    }

    private List<String> getStudentsListByStreamMethod(StudentRepository studentRepository) {
        return studentRepository
                .findAll()
                .stream()
                .map(student -> student.getName())
                .collect(Collectors.toList());
    }

    private void printStudentsNameToConsole(Integer startPoint, Integer endPoint, List<String> students) {
        for (int i = startPoint; i < endPoint; i++) {
            System.out.println(students.get(i));
        }
    }

    private synchronized void printStudentsNameToConsoleSynchronizedMethod(Integer startPoint, Integer endPoint, List<String> students) {
        for (int i = startPoint; i < endPoint; i++) {
            System.out.println(students.get(i));
        }
    }
}
