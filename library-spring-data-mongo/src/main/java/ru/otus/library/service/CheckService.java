package ru.otus.library.service;

import ru.otus.library.util.EntityContainer;

public interface CheckService {
    EntityContainer checkAndGetEntities(String bookId, String year, String authorId, String genreId, String commentId);
}
