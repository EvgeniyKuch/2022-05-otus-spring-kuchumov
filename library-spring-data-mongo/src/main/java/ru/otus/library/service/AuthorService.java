package ru.otus.library.service;

public interface AuthorService {
    String getAllAuthors();

    String insertAuthor(String firstName, String lastName);

    String updateAuthor(String authorId, String firstName, String lastName);

    String deleteById(String id);
}
