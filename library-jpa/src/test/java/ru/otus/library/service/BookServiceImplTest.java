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
import ru.otus.library.util.EntityContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {BookServiceImpl.class})
class BookServiceImplTest {

    @MockBean
    private BookDAO bookDAO;

    @MockBean
    private AuthorDAO authorDAO;

    @MockBean
    private GenreDAO genreDAO;

    @MockBean
    private CheckService checkService;

    @Autowired
    private BookService bookService;

    @Test
    void shouldInsertBookAndReturnItsString() {
        given(bookDAO.save(any())).willReturn(expectedBook());
        given(checkService.checkAndGetEntities(nullable(String.class), anyString(), anyString(), anyString(), nullable(String.class)))
                .willReturn(new EntityContainer());
        assertThat(bookService.insertBook("Евгений Онегин", "1833", "1", "1")).isEqualTo(
                "Добавлена книга" + System.lineSeparator() +
                        "Id: 1\tНазвание: Евгений Онегин\tГод: 1833\tАвтор: Александр Пушкин\tЖанр: Поэма");
    }


    @Test
    void shouldUpdateBookAndReturnItsString() {
        var container = new EntityContainer();
        container.setBook(new Book());
        given(checkService.checkAndGetEntities(anyString(), anyString(), anyString(), anyString(), nullable(String.class)))
                .willReturn(container);
        given(bookDAO.save(any())).willReturn(expectedBook());
        assertThat(bookService.updateBook("1", "Евгений Онегин", "1833", "1", "1")).isEqualTo(
                "Обновлена книга" + System.lineSeparator() +
                        "Id: 1\tНазвание: Евгений Онегин\tГод: 1833\tАвтор: Александр Пушкин\tЖанр: Поэма");
    }

    @Test
    void shouldReturnAllBooks() {
        given(bookDAO.findAll()).willReturn(List.of(expectedBook()));
        assertThat(bookService.getAllBooks()).isEqualTo(
                "Id: 1\tНазвание: Евгений Онегин\tГод: 1833\tАвтор: Александр Пушкин\tЖанр: Поэма");
    }

    @Test
    void shouldReturnStringBookById() {
        var container = new EntityContainer();
        container.setId(1L);
        given(checkService.checkDigit(anyString())).willReturn(container);
        given(bookDAO.findById(anyLong())).willReturn(Optional.of(expectedBook()));
        assertThat(bookService.getBookById("1")).isEqualTo(
                "Id: 1\tНазвание: Евгений Онегин\tГод: 1833\tАвтор: Александр Пушкин\tЖанр: Поэма" + System.lineSeparator() +
                        "Комментарии к книге отсутствуют");
    }

    private Book expectedBook() {
        Author author = new Author(1L, "Александр", "Пушкин");
        Genre genre = new Genre(1L, "Поэма");
        return new Book(1L, "Евгений Онегин", 1833, author, genre, new ArrayList<>());
    }
}
