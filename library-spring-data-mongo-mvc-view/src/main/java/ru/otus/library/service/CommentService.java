package ru.otus.library.service;

import ru.otus.library.domain.Comment;

public interface CommentService {
    Comment getCommentById(String id);

    Comment save(Comment comment);

    void deleteCommentById(String id);
}
