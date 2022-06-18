package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerCrmTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;;

    @Test
    public void contextLoads() throws Exception{
        Assertions.assertNotNull(studentController);
    }

    @Test
    public void testGetStudent() throws Exception {
        Assertions.assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class));
    }

    @Test
    public void testGetStudentFilteredByAge() throws Exception {
        Assertions.assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/student/filter/by-age/", String.class));
    }

    @Test
    public void testGetStudentFilteredByFaculty() throws Exception {
        Assertions.assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/student/filter/faculty/", String.class));
    }

    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Василий Васильев");
        student.setAge(33);

        Assertions.assertNotNull(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class));
    }

    @Test
    public void testPutStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Василий Васильев");
        student.setAge(27);

        this.restTemplate.put("http://localhost:" + port + "/student", student);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        this.restTemplate.delete("http://localhost:" + port + "/student",
                new Object[]{String.class});
    }

}
