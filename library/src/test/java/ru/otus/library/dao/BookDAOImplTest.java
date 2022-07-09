package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BookDAOImpl.class)
class BookDAOImplTest {

    @Autowired
    private BookDAO bookDAO;

    @Test
    void shouldInsertBook() {
        Long id = bookDAO.addBook(bookForAdd());
        assertThat(bookDAO.getBookById(id))
                .usingRecursiveComparison()
                .isEqualTo(expectedBookAfterAdd());
    }

    @Test
    void shouldUpdateBook() {
        bookDAO.updateBook(bookForUpdate());
        assertThat(bookDAO.getBookById(1L)).usingRecursiveComparison().isEqualTo(expectedBookAfterUpdate());
    }

    @Test
    void shouldDeleteBookById() {
        bookDAO.deleteBookById(1L);
        assertThat(bookDAO.checkExistId(1L)).isFalse();
    }

    @Test
    void shouldReturnBookById() {
        assertThat(bookDAO.getBookById(1L)).usingRecursiveComparison().isEqualTo(expectedBookForGet());
    }

    @Test
    void shouldReturnAllBooks() {
        assertThat(bookDAO.getAllBooks())
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(expectedBookForGet()));
    }

    @Test
    void shouldCheckExistId() {
        assertThat(bookDAO.checkExistId(1L)).isTrue();
        assertThat(bookDAO.checkExistId(2L)).isFalse();
    }

    private Book expectedBookForGet() {
        Author author = new Author(1L, "Александр", "Пушкин");
        Genre genre = new Genre(1L, "Поэма");
        return new Book(1L, "Руслан и Людмила", 1820, author, genre);
    }

    private Book bookForAdd() {
        Author author = new Author();
        author.setId(1L);
        Genre genre = new Genre();
        genre.setId(1L);
        return new Book(null, "Евгений Онегин", 1833, author, genre);
    }

    private Book expectedBookAfterAdd() {
        Author author = new Author(1L, "Александр", "Пушкин");
        Genre genre = new Genre(1L, "Поэма");
        return new Book(2L, "Евгений Онегин", 1833, author, genre);
    }

    private Book bookForUpdate() {
        Author author = new Author();
        author.setId(1L);
        Genre genre = new Genre();
        genre.setId(1L);
        return new Book(1L, "Евгений Онегин", 1833, author, genre);
    }

    private Book expectedBookAfterUpdate() {
        Author author = new Author(1L, "Александр", "Пушкин");
        Genre genre = new Genre(1L, "Поэма");
        return new Book(1L, "Евгений Онегин", 1833, author, genre);
    }
}
