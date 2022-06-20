package ru.hogwarts.school;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.interfaces.StudentService;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static ru.hogwarts.school.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerCrmTests {

    @LocalServerPort
    private int port;

    @InjectMocks
    private StudentController studentController;

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    void setup() {
        DEFAULTURL = "http://localhost:" + port + "/student";
        Student student = new Student(100L, "Васийлий Уриевский", 33);
        Student student2 = new Student(200L, "Василий Супермен", 27);
        List<Student> students = Arrays.asList(student, student2);
        studentService.createStudent(student);
        when(studentRepository.findById(100L)).thenReturn(Optional.of(student));
        when(studentRepository.findByAge(any(Integer.class))).thenReturn(students);
        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(students);
        when(studentRepository.findAll()).thenReturn(students);
        when(studentRepository.findByFacultyId(100L)).thenReturn(students);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
    }

    @AfterEach
    void tearDown() {
        studentService.deleteStudent(100L);

    }

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertNotNull(studentController);
    }

    @Test
    public void getStudentById() throws Exception {
        Assertions.assertNotNull(this.restTemplate.getForObject(DEFAULTURL + DEFAULTREQUESTPARAMETERSTUDENTID, String.class));
        String expectedResponse = "{\"id\":100,\"name\":\"Васийлий Уриевский\",\"age\":33,\"faculty\":null}";
        ResponseEntity<String> responseOk
                = this.restTemplate.getForEntity(DEFAULTURL + DEFAULTREQUESTPARAMETERSTUDENTID, String.class);
        Assertions.assertEquals(HttpStatus.OK, responseOk.getStatusCode());
        Assertions.assertEquals(expectedResponse, responseOk.getBody().toString());
        ResponseEntity<String> responseBadRequest
                = this.restTemplate.getForEntity(DEFAULTURL + DEFAULTREQUESTPARAMETERSTUDENTID + "wrong", String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseBadRequest.getStatusCode());
        ResponseEntity<String> responseInternalServerError
                = this.restTemplate.getForEntity(DEFAULTURL + DEFAULTREQUESTPARAMETERSTUDENTID + 256, String.class);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseInternalServerError.getStatusCode());
    }

    @Test
    public void testGetStudentsList() throws Exception {
        Assertions.assertNotNull(this.restTemplate.getForObject(DEFAULTURL, String.class));
        String expectedResponse = "[{\"id\":100,\"name\":\"Васийлий Уриевский\",\"age\":33,\"faculty\":null},{\"id\":200,\"name\":\"Василий Супермен\",\"age\":27,\"faculty\":null}]";
        ResponseEntity<String> responseOk
                = this.restTemplate.getForEntity(DEFAULTURL, String.class);
        Assertions.assertEquals(HttpStatus.OK, responseOk.getStatusCode());
        Assertions.assertEquals(expectedResponse, responseOk.getBody().toString());
        ResponseEntity<String> responseNotFound
                = this.restTemplate.getForEntity(DEFAULTURL + "wrong", String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode());
    }

    @Test
    public void testGetStudentListByAge() throws Exception {
        Assertions.assertNotNull(this.restTemplate.getForObject(DEFAULTURL + DEFAULTREQUESTPARAMETERBYAGE, String.class));
        String expectedResponse = "[{\"id\":100,\"name\":\"Васийлий Уриевский\",\"age\":33,\"faculty\":null},{\"id\":200,\"name\":\"Василий Супермен\",\"age\":27,\"faculty\":null}]";
        ResponseEntity<String> responseOk
                = this.restTemplate.getForEntity(DEFAULTURL + DEFAULTREQUESTPARAMETERBYAGE, String.class);
        Assertions.assertEquals(HttpStatus.OK, responseOk.getStatusCode());
        Assertions.assertEquals(expectedResponse, responseOk.getBody().toString());
        ResponseEntity<String> responseBadRequest
                = this.restTemplate.getForEntity(DEFAULTURL + DEFAULTREQUESTPARAMETERBYAGE + "wrong", String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseBadRequest.getStatusCode());
    }

    @Test
    public void testGetStudentListByAgesBetween() throws Exception {
        Assertions.assertNotNull(this.restTemplate.getForObject(DEFAULTURL + DEFAULTREQUESTPARAMETERBETWEENAGES, String.class));
        String expectedResponse = "[{\"id\":100,\"name\":\"Васийлий Уриевский\",\"age\":33,\"faculty\":null},{\"id\":200,\"name\":\"Василий Супермен\",\"age\":27,\"faculty\":null}]";
        ResponseEntity<String> responseOk
                = this.restTemplate.getForEntity(DEFAULTURL + DEFAULTREQUESTPARAMETERBETWEENAGES, String.class);
        Assertions.assertEquals(HttpStatus.OK, responseOk.getStatusCode());
        Assertions.assertEquals(expectedResponse, responseOk.getBody().toString());
        ResponseEntity<String> responseBadRequest
                = this.restTemplate.getForEntity(DEFAULTURL + DEFAULTREQUESTPARAMETERBETWEENAGES + "wrong", String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseBadRequest.getStatusCode());
    }

    @Test
    public void testGetStudentListByFacultyId() throws Exception {
        Assertions.assertNotNull(this.restTemplate.getForObject(DEFAULTURL + DEFAULTREQUESTPARAMETERFACULTYID, String.class));
        String expectedResponse = "[{\"id\":100,\"name\":\"Васийлий Уриевский\",\"age\":33,\"faculty\":null},{\"id\":200,\"name\":\"Василий Супермен\",\"age\":27,\"faculty\":null}]";
        ResponseEntity<String> responseOk
                = this.restTemplate.getForEntity(DEFAULTURL + DEFAULTREQUESTPARAMETERFACULTYID, String.class);
        Assertions.assertEquals(HttpStatus.OK, responseOk.getStatusCode());
        Assertions.assertEquals(expectedResponse, responseOk.getBody().toString());
        ResponseEntity<String> responseBadRequest
                = this.restTemplate.getForEntity(DEFAULTURL + DEFAULTREQUESTPARAMETERFACULTYID + "wrong", String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseBadRequest.getStatusCode());
    }

    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student();
        String expectedResponse = "{\"id\":100,\"name\":\"Васийлий Уриевский\",\"age\":33,\"faculty\":null}";
        String response
                = this.restTemplate.postForObject(DEFAULTURL, student, String.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedResponse, response);
        HttpEntity<Student> entity = new HttpEntity<Student>(student);
        ResponseEntity<Student> responseOK = this.restTemplate.exchange(DEFAULTURL, HttpMethod.POST, entity, Student.class, 100L);
        Assertions.assertEquals(HttpStatus.OK, responseOK.getStatusCode());
        Assertions.assertEquals("Васийлий Уриевский", responseOK.getBody().getName());
    }

    @Test
    public void testPutStudent() throws Exception {
        Student student = new Student(100L, "Васийлий Уриевский", 33);
        when(studentRepository.existsById(any(Long.class))).thenReturn(true);
        HttpEntity<Student> entity = new HttpEntity<Student>(student);
        ResponseEntity<Student> responseOK = this.restTemplate.exchange(DEFAULTURL, HttpMethod.PUT, entity, Student.class, 100L);
        Assertions.assertNotNull(responseOK.getBody().getId());
        Assertions.assertEquals(HttpStatus.OK, responseOK.getStatusCode());
        Assertions.assertEquals("Васийлий Уриевский", responseOK.getBody().getName());
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student();
        HttpEntity<Student> entity = new HttpEntity<Student>(student);
        ResponseEntity<String> responseOk = this.restTemplate.exchange(DEFAULTURL + "/100", HttpMethod.DELETE, entity, String.class);
        Assertions.assertEquals(HttpStatus.OK, responseOk.getStatusCode());
    }

}
