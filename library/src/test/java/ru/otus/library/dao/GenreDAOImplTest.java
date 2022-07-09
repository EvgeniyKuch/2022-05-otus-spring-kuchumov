package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreDAOImpl.class)
class GenreDAOImplTest {

    @Autowired
    private GenreDAO genreDAO;

    @Test
    void shouldReturnAllGenres() {
        assertThat(genreDAO.getAllGenres()).usingRecursiveComparison()
                .isEqualTo(List.of(new Genre(1L, "Поэма")));
    }

    @Test
    void checkExistId() {
        assertThat(genreDAO.checkExistId(1L)).isTrue();
        assertThat(genreDAO.checkExistId(2L)).isFalse();
    }
}
