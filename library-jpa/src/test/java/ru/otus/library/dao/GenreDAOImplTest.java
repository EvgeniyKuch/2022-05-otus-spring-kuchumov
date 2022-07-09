package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GenreDAOImpl.class)
class GenreDAOImplTest {

    @Autowired
    private GenreDAO genreDAO;

    @Test
    void shouldFindAll() {
        assertThat(genreDAO.findAll()).usingRecursiveComparison()
                .isEqualTo(List.of(new Genre(1L, "Поэма")));
    }

    @Test
    void findById() {
        assertThat(genreDAO.findById(1L)).isPresent().get().usingRecursiveComparison()
                .isEqualTo(new Genre(1L, "Поэма"));

    }

    @Test
    void checkExistId() {
        assertThat(genreDAO.existsById(1L)).isTrue();
        assertThat(genreDAO.existsById(2L)).isFalse();
    }
}
