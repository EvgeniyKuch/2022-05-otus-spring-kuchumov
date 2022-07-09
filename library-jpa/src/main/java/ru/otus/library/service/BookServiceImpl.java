package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;

    private final GenreDAO genreDAO;

    private final AuthorDAO authorDAO;

    private final CheckService checkService;

    @Override
    @Transactional
    public String insertBook(String name, String year, String authorId, String genreId) {
        String incorrectInput = checkService.checkBook(null, year, authorId, genreId);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        Book book = bookDAO.save(bookInstance(null, name, Integer.parseInt(year),
                Long.parseLong(authorId), Long.parseLong(genreId)));
        return String.format("Добавлена книга%s%s", System.lineSeparator(), toString(book));
    }

    @Override
    @Transactional
    public String updateBook(String id, String name, String year, String authorId, String genreId) {
        String incorrectInput = checkService.checkBook(id, year, authorId, genreId);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        Book book = bookDAO.save(bookInstance(Long.parseLong(id), name, Integer.parseInt(year),
                Long.parseLong(authorId), Long.parseLong(genreId)));
        return String.format("Обновлена книга%s%s", System.lineSeparator(), toString(book));
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllBooks() {
        return bookDAO.findAll().stream().map(this::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    @Transactional(readOnly = true)
    public String getBookById(String id) {
        String incorrectInput = checkService.checkBook(id, null, null, null);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        return bookDAO.findById(Long.parseLong(id)).map(this::toStringWithComments).orElse("Книга не найдена");
    }

    @Override
    @Transactional
    public String deleteById(String id) {
        String incorrectInput = checkService.checkBook(id, null, null, null);
        if (!incorrectInput.isEmpty()) {
            return incorrectInput;
        }
        bookDAO.deleteBookById(Long.parseLong(id));
        return bookDAO.existsById(Long.parseLong(id))
                ? String.format("При удалении книги id=%s произошла ошибка", id)
                : String.format("Книга id=%s с комментариями удалена", id);
    }

    private Book bookInstance(Long bookId, String name, Integer year, Long authorId, Long genreId) {
        Author author = authorDAO.findById(authorId).orElse(new Author().setId(authorId));
        Genre genre = genreDAO.findById(genreId).orElse(new Genre().setId(genreId));
        return new Book(bookId, name, year, author, genre, null);
    }

    private String toString(Book book) {
        return String.format("Id: %d\tНазвание: %s\tГод: %d\tАвтор: %s %s\tЖанр: %s", book.getId(), book.getName(),
                book.getYear(), book.getAuthor().getFirstName(),
                book.getAuthor().getLastName(), book.getGenre().getName());
    }

    private String toString(Comment comment, Integer order) {
        return String.format("\t%d. %s (id = %d)", order, comment.getContent(), comment.getId());
    }

    private String toString(List<Comment> comments) {
        List<String> commentsString = new ArrayList<>();
        commentsString.add("Комментарии к книге:");
        for (int i = 0; i < comments.size(); i++) {
            commentsString.add(toString(comments.get(i), i + 1));
        }
        return commentsString.size() == 1 ? "Комментарии к книге отсутствуют"
                : String.join(System.lineSeparator(), commentsString);
    }

    private String toStringWithComments(Book book) {
        return String.join(System.lineSeparator(), toString(book), toString(book.getComments()));
    }
}
