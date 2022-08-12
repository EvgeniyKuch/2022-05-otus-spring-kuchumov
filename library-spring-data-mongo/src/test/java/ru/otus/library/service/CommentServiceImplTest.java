package ru.otus.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {CommentServiceImpl.class, CheckServiceImpl.class})
class CommentServiceImplTest {

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CheckService checkService;

    @Test
    void shouldReturnAllComments() {
        given(commentRepository.findAll()).willReturn(List.of(expectedComment()));
        assertThat(commentService.getAllComments()).isEqualTo(
                "Id: 1. Интересный сюжет. Книга: Евгений Онегин (id = 1). Автор: Александр Пушкин");
    }

    @Test
    void shouldReturnCommentById() {
        given(commentRepository.findById("1")).willReturn(Optional.of(expectedComment()));
        assertThat(commentService.getCommentById("1")).isEqualTo(
                "Id: 1. Интересный сюжет. Книга: Евгений Онегин (id = 1). Автор: Александр Пушкин");
    }

    @Test
    void shouldReturnMessageNotFoundCommentById() {
        given(commentRepository.findById("1")).willReturn(Optional.empty());
        assertThat(commentService.getCommentById("1")).isEqualTo(
                "Комментарий с id = 1 не найден");
    }

    @Test
    void shouldReturnAllCommentsByBookId() {
        given(bookRepository.findById("1")).willReturn(Optional.of(expectedBook()));
        assertThat(commentService.getAllCommentsByBookId("1")).isEqualTo(
                "Id: 1. Интересный сюжет. Книга: Евгений Онегин (id = 1). Автор: Александр Пушкин");
    }

    @Test
    void shouldAddNewComment() {
        given(bookRepository.findById("1")).willReturn(Optional.of(expectedComment().getBook()));
        given(commentRepository.save(any())).willReturn(expectedComment());
        assertThat(commentService.addNewComment("1", anyString())).isEqualTo(
                "Id: 1. Интересный сюжет. Книга: Евгений Онегин (id = 1). Автор: Александр Пушкин");
    }

    @Test
    void shouldUpdateContentComment() {
        var commentAfterUpdate = expectedComment();
        commentAfterUpdate.setContent("Много букв");
        given(commentRepository.findById("1")).willReturn(Optional.of(expectedComment()));
        given(commentRepository.save(any())).willReturn(commentAfterUpdate);
        assertThat(commentService.updateContentComment("1", "Много букв")).isEqualTo(
                "Id: 1. Много букв. Книга: Евгений Онегин (id = 1). Автор: Александр Пушкин");
    }

    private Comment expectedComment() {
        Author author = new Author("1", "Александр", "Пушкин");
        Genre genre = new Genre("1", "Поэма");
        Book book = new Book("1", "Евгений Онегин", 1833, author, genre, new ArrayList<>());
        return new Comment("1", book, "Интересный сюжет");
    }

    private Book expectedBook() {
        var comment = expectedComment();
        var book = comment.getBook();
        book.setComments(List.of(comment));
        return book;
    }
}
