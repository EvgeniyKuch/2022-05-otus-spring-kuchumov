package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.dao.CommentDAO;
import ru.otus.library.dao.GenreDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис провекри корректности пользовательского ввода
 */
@AllArgsConstructor
@Service
public class CheckServiceImpl implements CheckService {

    private final BookDAO bookDAO;

    private final AuthorDAO authorDAO;

    private final GenreDAO genreDAO;

    private final CommentDAO commentDAO;

    @Override
    public String checkBook(String bookId, String year, String authorId, String genreId) {
        return checkCorrectInput(bookId, year, authorId, genreId, null);
    }

    @Override
    public String checkComment(String commentId, String bookId) {
        return checkCorrectInput(bookId, null, null, null, commentId);
    }

    private String checkCorrectInput(String bookId, String year, String authorId, String genreId, String commentId) {
        List<String> incorrectly = new ArrayList<>();
        try {
            if (bookId != null && !bookDAO.existsById(Long.parseLong(bookId))) {
                incorrectly.add(String.format("Введён несуществующий id книги: %d", Long.parseLong(bookId)));
            }
            if (year != null && !(Integer.parseInt(year) > 0 && Integer.parseInt(year) < 3000)) {
                incorrectly.add(String.format("Введён некорректный год выхода: %s", year));
            }
            if (authorId != null && !authorDAO.existsById(Long.parseLong(authorId))) {
                incorrectly.add(String.format("Введён несуществующий id автора: %d", Long.parseLong(authorId)));
            }
            if (genreId != null && !genreDAO.existsById(Long.parseLong(genreId))) {
                incorrectly.add(String.format("Введён несуществующий id жанра: %d", Long.parseLong(genreId)));
            }
            if (commentId != null && !commentDAO.existsById(Long.parseLong(commentId))) {
                incorrectly.add(String.format("Введён несуществующий id комментария: %d", Long.parseLong(commentId)));
            }
        } catch (NumberFormatException e) {
            incorrectly.add(String.format("Некорректный ввод числа. %s", e.getMessage()));
        }
        return String.join(System.lineSeparator(), incorrectly);
    }
}
