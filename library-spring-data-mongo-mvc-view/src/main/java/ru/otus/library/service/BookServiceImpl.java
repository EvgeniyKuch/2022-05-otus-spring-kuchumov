package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Book;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookWithCommentsById(String id) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setComments(commentRepository.findAllByBookId(id));
                    return book;
                }).orElse(new Book());
    }

    @Override
    public Book getBookById(String id) {
        return bookRepository.findById(id).orElse(new Book());
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteAllByBookId(id);
        bookRepository.deleteById(id);
    }
}
