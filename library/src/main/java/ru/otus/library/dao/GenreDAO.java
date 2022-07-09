package ru.otus.library.dao;

import ru.otus.library.domain.Genre;

import java.util.List;

public interface GenreDAO {
    List<Genre> getAllGenres();

    boolean checkExistId(Long id);
}
