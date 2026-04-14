package com.in28minutes.springboot.rest.example.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/data-test.sql")
public class StudentResourceE2ETest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testDeleteStudentE2E() {
        webTestClient.delete().uri("/students/10002")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testUpdateStudentE2E() {
        Student update = new Student(10001L, "New Name", "NewID");
        webTestClient.put().uri("/students/10001")
                .bodyValue(update)
                .exchange()
                .expectStatus().isNoContent();
    }
}