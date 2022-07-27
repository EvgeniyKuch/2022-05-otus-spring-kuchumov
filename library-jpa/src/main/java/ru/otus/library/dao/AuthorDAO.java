package ru.otus.library.dao;

import ru.otus.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO {

    List<Author> findAll();

    Optional<Author> findById(Long id);

    boolean existsById(Long id);
}
