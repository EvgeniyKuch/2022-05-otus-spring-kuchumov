package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.dao.CommentDAO;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;

    private final BookDAO bookDAO;

    private final CheckService checkService;

    @Override
    @Transactional(readOnly = true)
    public String getAllComments() {
        return commentDAO.findAll().stream().map(this::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    @Transactional(readOnly = true)
    public String getCommentById(String id) {
        String incorrectInput = checkService.checkComment(id, null);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        return commentDAO.findById(Long.parseLong(id)).map(this::toString)
                .orElse(String.format("Комментарий с id = %s не найден", id));
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllCommentsByBookId(String bookId) {
        String incorrectInput = checkService.checkComment(null, bookId);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        String comments = commentDAO.findAllByBookId(Long.parseLong(bookId)).stream().map(this::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        return !comments.isEmpty() ? comments : String.format("Комментарии к книге c id = %s не найдены", bookId);
    }

    @Override
    @Transactional
    public String addNewComment(String bookId, String content) {
        String incorrectInput = checkService.checkComment(null, bookId);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        Book book = bookDAO.findById(Long.parseLong(bookId)).orElse(new Book().setId(Long.parseLong(bookId)));
        Comment newComment = new Comment().setContent(content).setBook(book);
        return String.format("Добавлен комментарий:%s%s", System.lineSeparator(), toString(commentDAO.save(newComment)));
    }

    @Override
    @Transactional
    public String updateContentComment(String commentId, String newContent) {
        String incorrectInput = checkService.checkComment(commentId, null);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        commentDAO.updateContentById(Long.parseLong(commentId), newContent);
        return commentDAO.findById(Long.parseLong(commentId)).map(this::toString)
                .orElse(String.format("Произошла ошибка при изменении комментария с id = %s", commentId));
    }

    @Override
    @Transactional
    public String deleteCommentById(String id) {
        String incorrectInput = checkService.checkComment(id, null);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        commentDAO.deleteById(Long.parseLong(id));
        return !commentDAO.existsById(Long.parseLong(id))
                ? String.format("Комментарий с id = %s удалён", id)
                : String.format("При удалении комментария с id = %s произошла ошибка", id);
    }

    private String toString(Comment comment) {
        return String.format("Id: %d. %s. Книга: %s (id = %d). Автор: %s %s",
                comment.getId(), comment.getContent(),
                comment.getBook().getName(), comment.getBook().getId(),
                comment.getBook().getAuthor().getFirstName(),
                comment.getBook().getAuthor().getLastName());
    }
}
