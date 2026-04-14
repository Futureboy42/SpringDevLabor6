package com.in28minutes.springboot.rest.example.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/data-test.sql")
public class StudentResourceMockMvcTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testRetrieveExistingStudentById() throws Exception {
        var result = new Student(10001L, "Ranga", "E1234567");
        mockMvc
            .perform(MockMvcRequestBuilders.get("/students/10001"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }
}