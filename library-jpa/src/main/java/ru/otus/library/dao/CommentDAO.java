package ru.otus.library.dao;

import ru.otus.library.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDAO {
    List<Comment> findAll();

    Optional<Comment> findById(Long id);

    Comment save(Comment comment);

    void deleteById(Long id);

    boolean existsById(Long id);
}
