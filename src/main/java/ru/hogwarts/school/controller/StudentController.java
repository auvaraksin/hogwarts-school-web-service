package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.interfaces.StudentService;
import ru.hogwarts.school.model.Student;


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

    @GetMapping
    public ResponseEntity getStudent(@RequestParam(required = false) Long studentId) {
        if (studentId != null) {
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.ok(studentService.getStudentAll());
    }

    @GetMapping("/filter/by-name-substring")
    public ResponseEntity getStudentListByName(@RequestParam(required = true) String substring) {
        return ResponseEntity.ok(studentService.getStudentsBySubstring(substring));
    }

    @GetMapping("/filter/by-age")
    public ResponseEntity getStudentListByAge(@RequestParam(required = false) Integer studentAge,
                                              @RequestParam(required = false) Integer minAge,
                                              @RequestParam(required = false) Integer maxAge) {
        if (studentAge != null) {
            if (studentAge < 14) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(studentService.getStudentsByAge(studentAge));
        }
        if (minAge != null && maxAge != null) {
            if (minAge < 14 || maxAge.intValue() < minAge.intValue()) {
                return ResponseEntity.badRequest().body("Request parameters are wrong");
            }
            return ResponseEntity.ok(studentService.getStudentByAgeBetween(minAge, maxAge));
        }
        return ResponseEntity.badRequest().body("Request parameters are missing");
    }

    @GetMapping("/filter/by-faculty")
    public ResponseEntity getStudentListByFaculty(@RequestParam(required = false) Long facultyId,
                                                  @RequestParam(required = false) Long studentId) {
        if (facultyId != null) {
            return ResponseEntity.ok(studentService.getStudentByFaculty(facultyId));
        }
        if (studentId != null) {
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(studentService.getStudentFaculty(studentId));
        }
        return ResponseEntity.badRequest().body("Request parameters are missing");
    }

    @GetMapping("/students-quantity-info")
    public ResponseEntity getStudentsQuantity() {
        return ResponseEntity.ok(studentService.getStudentsQuantity());
    }

    @GetMapping("/students-average-age-info")
    public ResponseEntity getStudentsAverageAge() {
        return ResponseEntity.ok(studentService.getStudentsAverageAge());
    }

    @GetMapping("/students-average-age-info-by-stream-method")
    public ResponseEntity getStudentsAverageAgeByStreamMethod() {
        return ResponseEntity.ok(studentService.getStudentsAverageAgeByStreamMethod());
    }

    @GetMapping("/list-of-last-five-students")
    public ResponseEntity getLastFiveStudentsList() {
        return ResponseEntity.ok(studentService.getLastFiveStudentsList());
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

    @GetMapping("/get-students-list-by-thread-method")
    public ResponseEntity getStudentsListByThreadMethod() {
        studentService.getStudentsListByThreadMethod();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-students-list-by-synchronized-thread-method")
    public ResponseEntity getStudentsListBySynchronizedThreadMethod() {
        studentService.getStudentsListBySynchronizedThreadMethod();
        return ResponseEntity.ok().build();
    }

}
