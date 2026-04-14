package com.in28minutes.springboot.rest.example.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentResource.class)
public class StudentResourceWebMvcTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    StudentRepository studentRepository;

    @Test
    void testRetrieveExistingStudentById() throws Exception {
        var mockStudent = new Student(10001L, "Ranga", "E1234567");

        Mockito.when(studentRepository.findById(10001L)).thenReturn(Optional.of(mockStudent));

        mockMvc.perform(MockMvcRequestBuilders.get("/students/10001"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockStudent)));

        Mockito.verify(studentRepository, Mockito.times(1)).findById(10001L);
    }
}