package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.dao.CommentDAO;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.util.EntityContainer;

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
    @Transactional(readOnly = true)
    public EntityContainer checkAndGetEntities(String bookId, String year, String authorId, String genreId, String commentId) {
        List<String> incorrectly = new ArrayList<>();
        EntityContainer entityContainer = new EntityContainer();
        try {
            if (year != null) {
                var yearInt = Integer.parseInt(year);
                if (!(yearInt > 0 && yearInt < 3000)) {
                    incorrectly.add(String.format("Введён некорректный год выхода: %s", year));
                } else {
                    entityContainer.setYear(yearInt);
                }
            }
            if (bookId != null) {
                Long bookIdLong = Long.parseLong(bookId);
                bookDAO.findById(bookIdLong).ifPresentOrElse(entityContainer::setBook,
                        () -> incorrectly.add(String.format("Введён несуществующий id книги: %d", bookIdLong)));
            }
            if (authorId != null) {
                Long authorIdLong = Long.parseLong(authorId);
                authorDAO.findById(authorIdLong).ifPresentOrElse(entityContainer::setAuthor,
                        () -> incorrectly.add(String.format("Введён несуществующий id автора: %d", authorIdLong)));
            }
            if (genreId != null) {
                Long genreIdLong = Long.parseLong(genreId);
                genreDAO.findById(genreIdLong).ifPresentOrElse(entityContainer::setGenre,
                        () -> incorrectly.add(String.format("Введён несуществующий id жанра: %d", genreIdLong)));
            }
            if (commentId != null) {
                Long commentIdLong = Long.parseLong(commentId);
                commentDAO.findById(commentIdLong).ifPresentOrElse(entityContainer::setComment,
                        () -> incorrectly.add(String.format("Введён несуществующий id комментария: %d", commentIdLong)));
            }
        } catch (NumberFormatException e) {
            incorrectly.add(String.format("Некорректный ввод числа. %s", e.getMessage()));
        }
        if (!incorrectly.isEmpty()) {
            entityContainer.setError(String.join(System.lineSeparator(), incorrectly));
        }
        return entityContainer;
    }

    @Override
    public EntityContainer checkDigit(String id) {
        EntityContainer entityContainer = new EntityContainer();
        long longId;
        try {
            longId = Long.parseLong(id);
            entityContainer.setId(longId);
        } catch (NumberFormatException e) {
            entityContainer.setError(String.format("Некорректный ввод числа. %s", e.getMessage()));
        }
        return entityContainer;
    }
}
