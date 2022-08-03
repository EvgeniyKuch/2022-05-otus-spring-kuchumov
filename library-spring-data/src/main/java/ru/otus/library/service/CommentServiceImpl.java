package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.CommentRepository;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CheckService checkService;

    @Override
    @Transactional(readOnly = true)
    public String getAllComments() {
        String comments = commentRepository.findAll().stream().map(this::toString).collect(Collectors.joining(System.lineSeparator()));
        return comments.isEmpty() ? "Комментарии отсутствуют" : comments;
    }

    @Override
    @Transactional(readOnly = true)
    public String getCommentById(String id) {
        var container = checkService.checkDigit(id);
        if (container.getError() != null) {
            return container.getError();
        }
        return commentRepository.findById(container.getId()).map(this::toString)
                .orElse(String.format("Комментарий с id = %s не найден", id));
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllCommentsByBookId(String bookId) {
        var container = checkService.checkAndGetEntities(bookId, null, null, null, null);
        if (container.getError() != null) {
            return container.getError();
        }
        String comments = container.getBook().getComments().stream().map(this::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        return comments.isEmpty() ? String.format("Комментарии к книге c id = %s не найдены", bookId) : comments;
    }

    @Override
    @Transactional
    public String addNewComment(String bookId, String content) {
        var container = checkService.checkAndGetEntities(bookId, null, null, null, null);
        if (container.getError() != null) {
            return container.getError();
        }
        Comment comment = new Comment();
        comment.setBook(container.getBook());
        comment.setContent(content);
        return toString(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public String updateContentComment(String commentId, String newContent) {
        var container = checkService.checkAndGetEntities(null, null, null, null, commentId);
        if (container.getError() != null) {
            return container.getError();
        }
        Comment comment = container.getComment();
        comment.setContent(newContent);
        return toString(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public String deleteCommentById(String id) {
        var container = checkService.checkDigit(id);
        if (container.getError() != null) {
            return container.getError();
        }
        commentRepository.deleteById(container.getId());
        return String.format("Комментарий с id = %s удалён", id);
    }

    private String toString(Comment comment) {
        return String.format("Id: %d. %s. Книга: %s (id = %d). Автор: %s %s",
                comment.getId(), comment.getContent(),
                comment.getBook().getName(), comment.getBook().getId(),
                comment.getBook().getAuthor().getFirstName(),
                comment.getBook().getAuthor().getLastName());
    }
}
