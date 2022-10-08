package ru.otus.library.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.CommentService;
import ru.otus.library.service.GenreService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "user", authorities = {"ROLE_RANDOM"})
    @Test
    void whenEditBookUserIsNotAllowed() throws Exception {
        mockMvc.perform(get("/book/edit/123"))
                .andExpect(status().isFound()).andExpect(redirectedUrl("/access-denied"));
    }

    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void whenEditBookAdminIsAllowed() throws Exception {
        given(bookService.getBookById(anyString())).willReturn(mockBook());
        mockMvc.perform(get("/book/edit/*"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", authorities = {"ROLE_RANDOM"})
    @Test
    void whenDeleteBookUserIsNotAllowed() throws Exception {
        mockMvc.perform(get("/book/delete/id"))
                .andExpect(status().isFound()).andExpect(redirectedUrl("/access-denied"));
    }

    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void whenDeleteBookAdminIsAllowed() throws Exception {
        mockMvc.perform(get("/book/delete/id"))
                .andExpect(status().isFound()).andExpect(redirectedUrl("/book"));
    }

    @WithMockUser(username = "user", authorities = {"ROLE_RANDOM"})
    @Test
    void whenSaveBookUserIsNotAllowed() throws Exception {
        mockMvc.perform(get("/book/add"))
                .andExpect(status().isFound()).andExpect(redirectedUrl("/access-denied"));
    }

    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void whenSaveBookAdminIsAllowed() throws Exception {
        mockMvc.perform(get("/book/add"))
                .andExpect(status().isOk());
    }

    private Book mockBook() {
        return new Book("123", "1236", 1958,
                new Author("123", "ivan", "ivanov"),
                new Genre("123", "comedy"));
    }
}
