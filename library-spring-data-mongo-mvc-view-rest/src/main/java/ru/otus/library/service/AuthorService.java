package ru.otus.library.service;

import ru.otus.library.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();

    Author findById(String id);

    Author save(Author author);

    void deleteById(String id);
}
