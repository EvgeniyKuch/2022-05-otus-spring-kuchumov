package ru.otus.library.service;

import ru.otus.library.domain.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> getAllBooks();

    Book getBookWithCommentsById(String id);

    Book getBookById(String id);

    void deleteById(String id);
}
