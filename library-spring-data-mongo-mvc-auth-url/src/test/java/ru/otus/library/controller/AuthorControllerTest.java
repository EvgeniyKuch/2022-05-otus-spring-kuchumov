package ru.otus.library.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.library.service.AuthorService;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorControllerTest {

    @Autowired
    private AuthorService authorService;

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
    void whenEditAuthorUserIsNotAllowed() throws Exception {
        mockMvc.perform(get("/author/edit/id"))
                .andExpect(status().isFound()).andExpect(redirectedUrl("/access-denied"));
    }

    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void whenEditAuthorAdminIsAllowed() throws Exception {
        mockMvc.perform(get("/book/edit/*"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", authorities = {"ROLE_RANDOM"})
    @Test
    void whenAddAuthorUserIsNotAllowed() throws Exception {
        mockMvc.perform(get("/author/add"))
                .andExpect(status().isFound()).andExpect(redirectedUrl("/access-denied"));
    }

    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void whenAddAuthorAdminIsAllowed() throws Exception {
        mockMvc.perform(get("/author/add"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", authorities = {"ROLE_RANDOM"})
    @Test
    void whenDeleteAuthorUserIsNotAllowed() throws Exception {
        mockMvc.perform(get("/author/delete/id"))
                .andExpect(status().isFound()).andExpect(redirectedUrl("/access-denied"));
    }

    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void whenDeleteAuthorAdminIsAllowed() throws Exception {
        mockMvc.perform(get("/author/delete/id"))
                .andExpect(status().isFound()).andExpect(redirectedUrl("/author"));
    }
}
