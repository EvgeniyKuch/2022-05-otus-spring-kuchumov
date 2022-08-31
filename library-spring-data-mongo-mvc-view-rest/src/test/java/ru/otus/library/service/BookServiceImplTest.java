package ru.otus.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.controller.exceptions.NotFoundException;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {BookServiceImpl.class})
class BookServiceImplTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private BookService bookService;

    @Test
    void shouldReturnBookById() {
        given(bookRepository.findById(anyString())).willReturn(Optional.of(expectedBook()));
        var book = expectedBook();
        assertThat(bookService.getBookById(anyString())).isEqualTo(book);
    }

    @Test
    void shouldReturnEmptyBookIfNotExist() {
        given(bookRepository.findById(anyString())).willReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.getBookById(anyString())).isInstanceOf(NotFoundException.class);
    }

    private Book expectedBook() {
        Author author = new Author("1", "Александр", "Пушкин");
        Genre genre = new Genre("1", "Поэма");
        return new Book("1", "Евгений Онегин", 1833, author, genre);
    }
}
