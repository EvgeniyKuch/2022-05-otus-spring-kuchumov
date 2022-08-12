package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
    public EntityContainer checkAndGetEntities(String bookId, String year, String authorId, String genreId, String commentId) {
        List<String> incorrectly = new ArrayList<>();
        EntityContainer entityContainer = new EntityContainer();
        if (year != null) {
            var yearInt = Integer.parseInt(year);
            if (!(yearInt > 0 && yearInt < 3000)) {
                incorrectly.add(String.format("Введён некорректный год выхода: %s", year));
            } else {
                entityContainer.setYear(yearInt);
            }
        }
        if (bookId != null) {
            bookRepository.findById(bookId).ifPresentOrElse(entityContainer::setBook,
                    () -> incorrectly.add(String.format("Введён несуществующий id книги: %s", bookId)));
        }
        if (authorId != null) {
            authorRepository.findById(authorId).ifPresentOrElse(entityContainer::setAuthor,
                    () -> incorrectly.add(String.format("Введён несуществующий id автора: %s", authorId)));
        }
        if (genreId != null) {
            genreRepository.findById(genreId).ifPresentOrElse(entityContainer::setGenre,
                    () -> incorrectly.add(String.format("Введён несуществующий id жанра: %s", genreId)));
        }
        if (commentId != null) {
            commentRepository.findById(commentId).ifPresentOrElse(entityContainer::setComment,
                    () -> incorrectly.add(String.format("Введён несуществующий id комментария: %s", commentId)));
        }
        if (!incorrectly.isEmpty()) {
            entityContainer.setError(String.join(System.lineSeparator(), incorrectly));
        }
        return entityContainer;
    }
}
