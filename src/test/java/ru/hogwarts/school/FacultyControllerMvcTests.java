package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.FacultyServiceImpl;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.TestConstants.*;

@WebMvcTest
public class FacultyControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarServiceImpl avatarService;

    @MockBean
    private StudentServiceImpl studentService;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void facultyControllerSaveTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class)))
                .thenReturn(FACULTY_DEFAULT);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_DEFAULT))
                .andExpect(jsonPath("$.name").value(NAME_DEFAULT))
                .andExpect(jsonPath("$.color").value(COLOR_DEFAULT));
    }

    @Test
    public void facultyControllerGetTest() throws Exception {
        List<Faculty> faculties = Arrays.asList(FACULTY_DEFAULT);
        when(facultyRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(FACULTY_DEFAULT));
        when(facultyRepository.findAll()).thenReturn(faculties);
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(any(String.class), any(String.class)))
                .thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(ID_DEFAULT))
                .andExpect(jsonPath("$[0].name").value((NAME_DEFAULT)))
                .andExpect(jsonPath("$[0].color").value(COLOR_DEFAULT));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?facultyId=" + ID_DEFAULT)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_DEFAULT))
                .andExpect(jsonPath("$.name").value(NAME_DEFAULT))
                .andExpect(jsonPath("$.color").value(COLOR_DEFAULT));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?name=" + NAME_DEFAULT + "&color=" + COLOR_DEFAULT)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(ID_DEFAULT))
                .andExpect(jsonPath("$[0].name").value((NAME_DEFAULT)))
                .andExpect(jsonPath("$[0].color").value(COLOR_DEFAULT));
    }

    @Test
    public void facultyControllerPutTest() throws Exception {
        when(facultyRepository.existsById(any(Long.class))).thenReturn(true);
        when(facultyRepository.save(any(Faculty.class)))
                .thenReturn(FACULTY_DEFAULT);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/faculty")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_DEFAULT))
                .andExpect(jsonPath("$.name").value(NAME_DEFAULT))
                .andExpect(jsonPath("$.color").value(COLOR_DEFAULT));
    }

    @Test
    public void facultyControllerDeleteTest() throws Exception {
        doNothing().when(facultyRepository).deleteById(any(Long.class));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + ID_DEFAULT))
                .andExpect(status().isOk());
    }
}
