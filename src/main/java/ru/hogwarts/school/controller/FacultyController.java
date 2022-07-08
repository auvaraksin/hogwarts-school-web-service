package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.interfaces.FacultyService;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createFaculty);
    }

    @GetMapping()
    public ResponseEntity getFaculty(@RequestParam(required = false) Long facultyId,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String color) {
        if (facultyId != null) {
            Faculty faculty = facultyService.getFacultyById(facultyId);
            if (faculty == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(faculty);
        }
        if (name != null || color != null) {
            return ResponseEntity.ok(facultyService.getFacultyByNameOrByColor(name, color));
        }
        return ResponseEntity.ok(facultyService.getFacultyAll());
    }

    @GetMapping("/find-faculties-with-the-longest-name")
    public ResponseEntity<Collection> getFacultiesWithTheLongestName() {
        return ResponseEntity.ok(facultyService.getFacultyWithTheMaxNameLength());
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updateFaculty = facultyService.updateFaculty(faculty);
        if (updateFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity deleteFaculty(@PathVariable Long facultyId) {
        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.ok().build();
    }
}
