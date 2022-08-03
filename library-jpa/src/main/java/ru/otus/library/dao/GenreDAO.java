package ru.otus.library.dao;

import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDAO {
    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    boolean existsById(Long id);
}
