package com.in28minutes.springboot.rest.example.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "/data-test.sql")
@AutoConfigureTestDatabase
public class StudentResourceIntegrationTest {

    @Autowired
    private StudentResource studentResource;

    @Test
    public void testThatAllStudentsCanBeRetrieved() {
        List<Student> students = studentResource.retrieveAllStudents();

        assertEquals(2, students.size());
        Assertions.assertTrue(students.stream().anyMatch(s -> s.getName().equals("Ranga")));
        Assertions.assertTrue(students.stream().anyMatch(s -> s.getName().equals("Ravi")));
    }
}
