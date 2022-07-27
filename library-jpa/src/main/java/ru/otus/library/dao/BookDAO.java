package ru.otus.library.dao;

import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDAO {

    Book save(Book book);

    void deleteBookById(Long id);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    boolean existsById(Long id);
}
