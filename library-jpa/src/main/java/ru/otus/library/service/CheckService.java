package ru.otus.library.service;

public interface CheckService {
    String checkBook(String id, String year, String authorId, String genreId);

    String checkComment(String commentId, String bookId);
}
