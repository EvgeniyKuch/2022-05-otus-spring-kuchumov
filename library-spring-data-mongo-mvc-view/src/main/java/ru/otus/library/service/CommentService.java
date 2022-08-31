package ru.otus.library.service;

import ru.otus.library.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment getCommentById(String id);

    List<Comment> getCommentByBookId(String bookId);

    Comment save(Comment comment);

    void deleteCommentById(String id);
}
