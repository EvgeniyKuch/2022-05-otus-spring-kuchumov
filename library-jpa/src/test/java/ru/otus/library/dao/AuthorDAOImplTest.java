package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorDAOImpl.class)
class AuthorDAOImplTest {

    @Autowired
    private AuthorDAO authorDAO;

    @Test
    void shouldReturnAllAuthors() {
        assertThat(authorDAO.findAll()).usingRecursiveComparison().isEqualTo(List.of(
                new Author(1L, "Александр", "Пушкин")));
    }

    @Test
    void shouldReturnAuthorById() {
        assertThat(authorDAO.findById(1L)).isPresent().get().usingRecursiveComparison()
                .isEqualTo(new Author(1L, "Александр", "Пушкин"));
    }

    @Test
    void shouldExistsById() {
        assertThat(authorDAO.existsById(1L)).isTrue();
        assertThat(authorDAO.existsById(2L)).isFalse();
    }
}
