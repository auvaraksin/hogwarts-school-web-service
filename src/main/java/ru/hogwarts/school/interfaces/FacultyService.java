package ru.hogwarts.school.interfaces;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Collection<Faculty> getFacultyAll();

    Faculty getFacultyById(Long facultyId);

    Collection<Faculty> getFacultyByNameOrByColor(String name, String color);

    Collection<Faculty> getFacultyWithTheMaxNameLength();

    Faculty updateFaculty(Faculty faculty);

    void deleteFaculty(Long facultyId);
}
