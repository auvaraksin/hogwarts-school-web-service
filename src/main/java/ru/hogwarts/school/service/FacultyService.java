package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Collection<Faculty> getFacultyAll() {
        return facultyRepository.findAll();
    }

    public Faculty getFacultyById(Long facultyId) {
        return facultyRepository.findById(facultyId).get();
    }

    public Collection<Faculty> getFacultyByNameOrByColor(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (facultyRepository.existsById(faculty.getId()) != true) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }
}
