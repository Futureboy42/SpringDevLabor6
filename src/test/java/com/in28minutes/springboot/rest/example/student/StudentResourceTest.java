package com.in28minutes.springboot.rest.example.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentResourceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentResource studentResource;

    @Test
    public void testThatRetrieveAllStudentsCallRepository() {
        var students = List.of(
            new Student(1L, "Béla", "ABC123"),
            new Student(2L, "Luca", "QWE456")
        );
        when(studentRepository.findAll()).thenReturn(students);
        var resp = studentResource.retrieveAllStudents();
        assertEquals(2, resp.size());
        Assertions.assertTrue(resp.stream().anyMatch(s -> s.getName().equals("Béla")));
        Assertions.assertTrue(resp.stream().anyMatch(s -> s.getName().equals("Luca")));
        Mockito.verify(studentRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testThatExistingStudentCanBeRetrievedById() {
        long id = 1L;
        Student student = new Student(id, "Béla", "ABC123");
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        Student resp = studentResource.retrieveStudent(id);

        assertEquals(student, resp);
        Mockito.verify(studentRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void testThatRetrievingNonExistingStudentThrowsException() {
        long id = 1L;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentResource.retrieveStudent(id));

        Mockito.verify(studentRepository, Mockito.times(1)).findById(id);
    }
}