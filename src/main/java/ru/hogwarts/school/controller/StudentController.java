package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createStudent);
    }

    @GetMapping()
    public ResponseEntity getStudent(@RequestParam(required = false) Long studentId,
                                     @RequestParam(required = false) Integer studentAge,
                                     @RequestParam(required = false) Integer minAge,
                                     @RequestParam(required = false) Integer maxAge,
                                     @RequestParam(required = false) Long facultyId,
                                     @RequestParam(required = false) Long studentIdGetFaculty) {
        if (studentId != null) {
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(student);
        }
        if (studentAge != null) {
            if (studentAge < 14) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(studentService.getStudentsByAge(studentAge));
        }
        if (minAge != null && maxAge != null) {
            if (minAge < 14 || maxAge.intValue() < minAge.intValue()) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(studentService.getStudentByAgeBetween(minAge, maxAge));
        }
        if (facultyId != null) {
            return ResponseEntity.ok(studentService.getStudentByFaculty(facultyId));
        }
        if (studentIdGetFaculty != null) {
            Student student = studentService.getStudentById(studentIdGetFaculty);
            if (student == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(studentService.getStudentFaculty(studentIdGetFaculty));
        }

        return ResponseEntity.ok(studentService.getStudentAll());
    }

    @PutMapping()
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        if (updateStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }
}
