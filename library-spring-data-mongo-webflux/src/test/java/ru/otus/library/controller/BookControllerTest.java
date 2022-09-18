package ru.otus.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest
class BookControllerTest {

    @Autowired
    private BookController bookController;

    @Test
    public void testAllBook() throws InterruptedException {
        WebTestClient client = WebTestClient
                .bindToController(bookController)
                .configureClient()
                .baseUrl("/allbook")
                .build();
        client.get()
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_VALUE)
                .expectBody(List.class)
        ;
    }

}
