package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.controller.exceptions.NotFoundException;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.CommentRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment getCommentById(String id) {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Комментарий с id = %s не найден", id)));
    }

    @Override
    public List<Comment> getCommentByBookId(String bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(String id) {
        commentRepository.deleteById(id);
    }
}
