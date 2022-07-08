package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.interfaces.FacultyService;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Method to create faculty was invoked ");
        return facultyRepository.save(faculty);
    }

    public Collection<Faculty> getFacultyAll() {
        logger.info("Method to find all faculties was invoked ");
        return facultyRepository.findAll();
    }

    public Faculty getFacultyById(Long facultyId) {
        logger.info("Method to find faculty by Id was invoked ");
        return facultyRepository.findById(facultyId).get();
    }

    public Collection<Faculty> getFacultyByNameOrByColor(String name, String color) {
        logger.info("Method to find faculty by faculty's name or by faculty's color was invoked");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Faculty> getFacultyWithTheMaxNameLength() {
        logger.info("Method to find faculties with max name length was invoked");
        int max = facultyRepository
                .findAll()
                .stream()
                .mapToInt(faculty ->faculty.getName().length())
                .max().getAsInt();
        return facultyRepository
                .findAll()
                .stream()
                .parallel()
                .filter(faculty -> faculty.getName().length() == max)
                .collect(Collectors.toList());
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (facultyRepository.existsById(faculty.getId()) != true) {
            logger.warn("There is no faculty with name = " + faculty.getName());
            return null;
        }
        logger.info("Method to update faculty's data was invoked");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        logger.info("Method to delete faculty was invoked");
        facultyRepository.deleteById(facultyId);
    }
}
