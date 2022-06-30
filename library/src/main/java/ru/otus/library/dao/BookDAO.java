package ru.otus.library.dao;

import ru.otus.library.domain.Book;

import java.util.List;

public interface BookDAO {

    Long addBook(Book book);

    void updateBook(Book book);

    void deleteBookById(Long id);

    Book getBookById(Long id);

    List<Book> getAllBooks();

    boolean checkExistId(Long id);
}
