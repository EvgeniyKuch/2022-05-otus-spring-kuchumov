package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.util.EntityContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис провекри корректности пользовательского ввода
 */
@AllArgsConstructor
@Service
public class CheckServiceImpl implements CheckService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

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
                bookRepository.findById(bookIdLong).ifPresentOrElse(entityContainer::setBook,
                        () -> incorrectly.add(String.format("Введён несуществующий id книги: %d", bookIdLong)));
            }
            if (authorId != null) {
                Long authorIdLong = Long.parseLong(authorId);
                authorRepository.findById(authorIdLong).ifPresentOrElse(entityContainer::setAuthor,
                        () -> incorrectly.add(String.format("Введён несуществующий id автора: %d", authorIdLong)));
            }
            if (genreId != null) {
                Long genreIdLong = Long.parseLong(genreId);
                genreRepository.findById(genreIdLong).ifPresentOrElse(entityContainer::setGenre,
                        () -> incorrectly.add(String.format("Введён несуществующий id жанра: %d", genreIdLong)));
            }
            if (commentId != null) {
                Long commentIdLong = Long.parseLong(commentId);
                commentRepository.findById(commentIdLong).ifPresentOrElse(entityContainer::setComment,
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
