package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final CheckService checkService;

    @Override
    @Transactional
    public String insertBook(String name, String year, String authorId, String genreId) {
        var container = checkService.checkAndGetEntities(null, year, authorId, genreId, null);
        if (container.getError() != null) {
            return container.getError();
        }
        Book book = bookRepository.save(new Book(null,
                name, container.getYear(), container.getAuthor(), container.getGenre(), new ArrayList<>()));
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
        String result = toString(bookRepository.save(bookRepository.save(book)));
        book.setComments(null);
        List<Comment> commentsForUpdate = commentRepository.findAllByBookId(book.getId());
        commentsForUpdate.forEach(comment -> comment.setBook(book));
        commentRepository.saveAll(commentsForUpdate);
        return String.format("Обновлена книга%s%s", System.lineSeparator(), result);
    }

    @Override
    @Transactional
    public String getAllBooks() {
        return bookRepository.findAll().stream().map(this::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    @Transactional
    public String getBookById(String id) {
        return bookRepository.findById(id).map(this::toStringWithComments).orElse("Книга не найдена");
    }

    @Override
    @Transactional
    public String deleteById(String id) {
        commentRepository.deleteAllByBookId(id);
        bookRepository.deleteById(id);
        return String.format("Книга id=%s с комментариями удалена", id);
    }

    private String toString(Book book) {
        return String.format("Id: %s\tНазвание: %s\tГод: %d\tАвтор: %s %s\tЖанр: %s", book.getId(), book.getName(),
                book.getYear(), book.getAuthor().getFirstName(),
                book.getAuthor().getLastName(), book.getGenre().getName());
    }

    private String toString(Comment comment, Integer order) {
        return String.format("\t%d. %s (id = %s)", order, comment.getContent(), comment.getId());
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
