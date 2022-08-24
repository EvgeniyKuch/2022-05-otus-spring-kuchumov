package ru.otus.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void shouldReturnBookByIdWithComments() {
        given(bookRepository.findById(anyString())).willReturn(Optional.of(expectedBook()));
        given(commentRepository.findAllByBookId(anyString())).willReturn(expectedComments());
        var book = expectedBook();
        book.setComments(expectedComments());
        assertThat(bookService.getBookWithCommentsById(anyString())).isEqualTo(book);
    }

    @Test
    void shouldReturnEmptyBookIfNotExist() {
        given(bookRepository.findById(anyString())).willReturn(Optional.empty());
        assertThat(bookService.getBookWithCommentsById(anyString())).isEqualTo(new Book());
    }

    private Book expectedBook() {
        Author author = new Author("1", "Александр", "Пушкин");
        Genre genre = new Genre("1", "Поэма");
        return new Book("1", "Евгений Онегин", 1833, author, genre, new ArrayList<>());
    }

    private List<Comment> expectedComments() {
        return List.of(
                new Comment("1", null, "Хорошо"),
                new Comment("1", null, "Плохо")
        );
    }
}
