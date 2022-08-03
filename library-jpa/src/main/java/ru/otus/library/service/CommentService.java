package ru.otus.library.service;

public interface CommentService {
    String getAllComments();

    String getCommentById(String id);

    String getAllCommentsByBookId(String bookId);

    String addNewComment(String bookId, String content);

    String updateContentComment(String commentId, String newContent);

    String deleteCommentById(String id);
}
