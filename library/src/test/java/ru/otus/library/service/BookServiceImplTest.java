package ru.otus.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {BookServiceImpl.class})
class BookServiceImplTest {

    @MockBean
    private BookDAO bookDAO;

    @MockBean
    private AuthorDAO authorDAO;

    @MockBean
    private GenreDAO genreDAO;

    @Autowired
    private BookService bookService;

    @Test
    void shouldInsertBookAndReturnItsString() {
        given(authorDAO.checkExistId(1L)).willReturn(true);
        given(genreDAO.checkExistId(1L)).willReturn(true);
        given(bookDAO.getBookById(anyLong())).willReturn(expectedBook());
        assertThat(bookService.insertBook("Евгений Онегин", "1833", "1", "1")).isEqualTo(
                "Добавлена книга" + System.lineSeparator() +
                        "Id: 1\tНазвание: Евгений Онегин\tГод: 1833\tАвтор: Александр Пушкин\tЖанр: Поэма");
    }

    @Test
    void shouldNotInsertBookIfNotExistAuthorOrGenreOrIncorrectYearAndReturnErrorString() {
        assertThat(bookService.insertBook("Евгений Онегин", "3025", "1", "1")).isEqualTo(
                "Введён некорректный год выхода: 3025" + System.lineSeparator() +
                        "Введён несуществующий id автора: 1" + System.lineSeparator() +
                        "Введён несуществующий id жанра: 1");
    }

    @Test
    void shouldNotInsertBookIfInputIncorrectNumberAndReturnErrorString() {
        assertThat(bookService.insertBook("Евгений Онегин", "abcd", "1", "1")).isEqualTo(
                "Некорректный ввод числа. For input string: \"abcd\"");
    }

    @Test
    void shouldUpdateBookAndReturnItsString() {
        given(bookDAO.checkExistId(1L)).willReturn(true);
        given(authorDAO.checkExistId(1L)).willReturn(true);
        given(genreDAO.checkExistId(1L)).willReturn(true);
        given(bookDAO.getBookById(anyLong())).willReturn(expectedBook());
        assertThat(bookService.updateBook("1", "Евгений Онегин", "1833", "1", "1")).isEqualTo(
                "Обновлена книга" + System.lineSeparator() +
                        "Id: 1\tНазвание: Евгений Онегин\tГод: 1833\tАвтор: Александр Пушкин\tЖанр: Поэма");
    }

    @Test
    void shouldReturnAllBooks() {
        given(bookDAO.getAllBooks()).willReturn(List.of(expectedBook()));
        assertThat(bookService.getAllBooks()).isEqualTo(
                "Id: 1\tНазвание: Евгений Онегин\tГод: 1833\tАвтор: Александр Пушкин\tЖанр: Поэма");
    }

    @Test
    void shouldReturnStringBookById() {
        given(bookDAO.checkExistId(anyLong())).willReturn(true);
        given(bookDAO.getBookById(anyLong())).willReturn(expectedBook());
        assertThat(bookService.getBookById("1")).isEqualTo(
                "Id: 1\tНазвание: Евгений Онегин\tГод: 1833\tАвтор: Александр Пушкин\tЖанр: Поэма");
    }

    @Test
    void shouldDeleteByIdAndReturnSuccessString() {
        given(bookDAO.checkExistId(anyLong())).willReturn(true).willReturn(false);
        assertThat(bookService.deleteById("1")).isEqualTo("Книга id=1 удалена");
    }

    @Test
    void shouldNotDeleteByIdWhenErrorAndReturnErrorString() {
        given(bookDAO.checkExistId(anyLong())).willReturn(true).willReturn(true);
        assertThat(bookService.deleteById("1")).isEqualTo("При удалении книги id=1 произошла ошибка");
    }

    private Book expectedBook() {
        Author author = new Author(1L, "Александр", "Пушкин");
        Genre genre = new Genre(1L, "Поэма");
        return new Book(1L, "Евгений Онегин", 1833, author, genre);
    }
}
