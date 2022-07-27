package ru.otus.library.service;

public interface BookService {
    String insertBook(String name, String year, String authorId, String genreId);

    String updateBook(String id, String name, String year, String authorId, String genreId);

    String getAllBooks();

    String getBookById(String id);

    String deleteById(String id);
}
