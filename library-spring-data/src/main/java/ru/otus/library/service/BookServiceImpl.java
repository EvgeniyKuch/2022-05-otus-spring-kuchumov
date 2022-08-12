package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CheckService checkService;

    @Override
    @Transactional
    public String insertBook(String name, String year, String authorId, String genreId) {
        var container = checkService.checkAndGetEntities(null, year, authorId, genreId, null);
        if (container.getError() != null) {
            return container.getError();
        }
        Book book = bookRepository.save(new Book(null,
                name, container.getYear(), container.getAuthor(), container.getGenre(), null));
        return String.format("Добавлена книга%s%s", System.lineSeparator(), toString(book));
    }

    @Override
    @Transactional
    public String updateBook(String id, String name, String year, String authorId, String genreId) {
        var container = checkService.checkAndGetEntities(id, year, authorId, genreId, null);
        if (container.getError() != null) {
            return container.getError();
        }
        Book book = container.getBook();
        book.setName(name);
        book.setYear(container.getYear());
        book.setAuthor(container.getAuthor());
        book.setGenre(container.getGenre());
        return String.format("Обновлена книга%s%s", System.lineSeparator(), toString(bookRepository.save(book)));
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllBooks() {
        return bookRepository.findAll().stream().map(this::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    @Transactional(readOnly = true)
    public String getBookById(String id) {
        var container = checkService.checkDigit(id);
        if (container.getError() != null) {
            return container.getError();
        }
        return bookRepository.findById(container.getId()).map(this::toStringWithComments).orElse("Книга не найдена");
    }

    @Override
    @Transactional
    public String deleteById(String id) {
        var container = checkService.checkDigit(id);
        if (container.getError() != null) {
            return container.getError();
        }
        bookRepository.deleteById(container.getId());
        return String.format("Книга id=%s с комментариями удалена", id);
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
