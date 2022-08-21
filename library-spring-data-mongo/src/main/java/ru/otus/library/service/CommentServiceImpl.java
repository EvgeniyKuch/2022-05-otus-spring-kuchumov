package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CheckService checkService;

    @Override
    public String getAllComments() {
        String comments = commentRepository.findAll().stream().map(this::toString).collect(Collectors.joining(System.lineSeparator()));
        return comments.isEmpty() ? "Комментарии отсутствуют" : comments;
    }

    @Override
    @Transactional
    public String getCommentById(String id) {
        return commentRepository.findById(id).map(this::toString)
                .orElse(String.format("Комментарий с id = %s не найден", id));
    }

    @Override
    public String getAllCommentsByBookId(String bookId) {
        var comments = commentRepository.findAllByBookId(bookId).stream().map(this::toString)
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
        var book = container.getBook();
        Comment comment = new Comment();
        comment.setBook(book);
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
        commentRepository.deleteById(id);
        return String.format("Комментарий с id = %s удалён", id);
    }

    private String toString(Comment comment) {
        String author = comment.getBook().getAuthor() != null
                ? String.format("%s %s", comment.getBook().getAuthor().getFirstName(), comment.getBook().getAuthor().getLastName())
                : "Не найден";
        return String.format("Id: %s. %s. Книга: %s (id = %s). Автор: %s",
                comment.getId(), comment.getContent(),
                comment.getBook().getName(), comment.getBook().getId(), author);
    }
}
