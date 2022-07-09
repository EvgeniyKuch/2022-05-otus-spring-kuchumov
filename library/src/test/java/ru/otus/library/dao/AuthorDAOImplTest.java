package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorDAOImpl.class)
class AuthorDAOImplTest {

    @Autowired
    private AuthorDAO authorDAO;

    @Test
    void shouldReturnAllAuthors() {
        assertThat(authorDAO.getAllAuthors())
                .usingRecursiveComparison().isEqualTo(List.of(
                        new Author(1L, "Александр", "Пушкин"))
        );
    }

    @Test
    void shouldCheckExistId() {
        assertThat(authorDAO.checkExistId(1L)).isTrue();
        assertThat(authorDAO.checkExistId(2L)).isFalse();
    }
}
