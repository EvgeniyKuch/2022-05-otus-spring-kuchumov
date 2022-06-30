package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;

    private final GenreDAO genreDAO;

    private final AuthorDAO authorDAO;

    public BookServiceImpl(BookDAO bookDAO, GenreDAO genreDAO, AuthorDAO authorDAO) {
        this.bookDAO = bookDAO;
        this.genreDAO = genreDAO;
        this.authorDAO = authorDAO;
    }

    @Override
    public String insertBook(String name, String year, String authorId, String genreId) {
        String incorrectInput = checkCorrectInput(null, year, authorId, genreId);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        Long newBookId = bookDAO.addBook(bookInstance(null, name, Integer.parseInt(year),
                Long.parseLong(authorId), Long.parseLong(genreId)));
        return String.format("Добавлена книга%s%s", System.lineSeparator(),
                toString(bookDAO.getBookById(newBookId)));
    }

    @Override
    public String updateBook(String id, String name, String year, String authorId, String genreId) {
        String incorrectInput = checkCorrectInput(id, year, authorId, genreId);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        bookDAO.updateBook(bookInstance(Long.parseLong(id), name, Integer.parseInt(year),
                Long.parseLong(authorId), Long.parseLong(genreId)));
        return String.format("Обновлена книга%s%s", System.lineSeparator(),
                toString(bookDAO.getBookById(Long.parseLong(id))));
    }

    @Override
    public String getAllBooks() {
        return bookDAO.getAllBooks().stream().map(this::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String getBookById(String id) {
        String incorrectInput = checkCorrectInput(id, null, null, null);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        return toString(bookDAO.getBookById(Long.parseLong(id)));
    }

    @Override
    public String deleteById(String id) {
        String incorrectInput = checkCorrectInput(id, null, null, null);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        bookDAO.deleteBookById(Long.parseLong(id));
        if (bookDAO.checkExistId(Long.parseLong(id))) {
            return String.format("При удалении книги id=%s произошла ошибка", id);
        }
        return String.format("Книга id=%s удалена", id);
    }

    private String checkCorrectInput(String id, String year, String authorId, String genreId) {
        List<String> incorrectly = new ArrayList<>();
        try {
            if (id != null && !bookDAO.checkExistId(Long.parseLong(id))) {
                incorrectly.add(String.format("Введён несуществующий id книги: %d", Long.parseLong(id)));
            }
            if (year != null && !(Integer.parseInt(year) > 0 && Integer.parseInt(year) < 3000)) {
                incorrectly.add(String.format("Введён некорректный год выхода: %s", year));
            }
            if (authorId != null && !authorDAO.checkExistId(Long.parseLong(authorId))) {
                incorrectly.add(String.format("Введён несуществующий id автора: %d", Long.parseLong(authorId)));
            }
            if (genreId != null && !genreDAO.checkExistId(Long.parseLong(genreId))) {
                incorrectly.add(String.format("Введён несуществующий id жанра: %d", Long.parseLong(genreId)));
            }
        } catch (NumberFormatException e) {
            incorrectly.add(String.format("Некорректный ввод числа. %s", e.getMessage()));
        }
        return String.join(System.lineSeparator(), incorrectly);
    }

    private Book bookInstance(Long bookId, String name, Integer year, Long authorId, Long genreId) {
        Author author = new Author();
        author.setId(authorId);
        Genre genre = new Genre();
        genre.setId(genreId);
        return new Book(bookId, name, year, author, genre);
    }

    private String toString(Book book) {
        return String.format("Id: %d\tНазвание: %s\tГод: %d\tАвтор: %s %s\tЖанр: %s", book.getId(), book.getName(),
                book.getYear(), book.getAuthor().getFirstName(),
                book.getAuthor().getLastName(), book.getGenre().getName());
    }
}
