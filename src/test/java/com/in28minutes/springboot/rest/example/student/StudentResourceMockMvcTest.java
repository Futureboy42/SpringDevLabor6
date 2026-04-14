package com.in28minutes.springboot.rest.example.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    void testCreateStudent() throws Exception {
        var newStudent = new Student(null, "János", "P987654");

        mockMvc
                .perform(MockMvcRequestBuilders.post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student updatedData = new Student(10001L, "Ranga Updated", "E1234567");
        mockMvc.perform(MockMvcRequestBuilders.put("/students/10001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedData)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateNonExistingStudent() throws Exception {
        Student updatedData = new Student(99999L, "Senki", "X00000");
        mockMvc.perform(MockMvcRequestBuilders.put("/students/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedData)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/10001"))
                .andExpect(status().isOk());
    }
}