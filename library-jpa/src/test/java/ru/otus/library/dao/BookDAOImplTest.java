package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookDAOImpl.class)
class BookDAOImplTest {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldInsertBook() {
        Book book = bookDAO.save(bookForAdd());
        assertThat(em.find(Book.class, book.getId()))
                .usingRecursiveComparison()
                .isEqualTo(expectedBookAfterAdd());
    }

    @Test
    void shouldUpdateBook() {
        Book book = em.find(Book.class, 1L);
        book.setName("Евгений Онегин");
        book.setYear(1833);
        bookDAO.save(book);
        assertThat(em.find(Book.class, 1L)).usingRecursiveComparison()
                .ignoringFields("comments")
                .isEqualTo(expectedBookAfterUpdate());
    }

    @Test
    void shouldDeleteBookById() {
        bookDAO.deleteBookById(1L);
        assertThat(bookDAO.existsById(1L)).isFalse();
    }

    @Test
    void shouldReturnBookById() {
        assertThat(bookDAO.findById(1L)).isPresent().get()
                .usingRecursiveComparison()
                .ignoringFields("comments")
                .isEqualTo(expectedBookForGet());
    }

    @Test
    void shouldReturnAllBooks() {
        assertThat(bookDAO.findAll())
                .usingRecursiveComparison()
                .ignoringFields("comments")
                .isEqualTo(List.of(expectedBookForGet()));
    }

    @Test
    void shouldCheckExistId() {
        assertThat(bookDAO.existsById(1L)).isTrue();
        assertThat(bookDAO.existsById(2L)).isFalse();
    }

    private Book expectedBookForGet() {
        Author author = new Author(1L, "Александр", "Пушкин");
        Genre genre = new Genre(1L, "Поэма");
        return new Book(1L, "Руслан и Людмила", 1820, author, genre, null);
    }

    private Book bookForAdd() {
        Author author = new Author(1L, "Александр", "Пушкин");
        Genre genre = new Genre(1L, "Поэма");
        return new Book(null, "Евгений Онегин", 1833, author, genre, null);
    }

    private Book expectedBookAfterAdd() {
        Author author = new Author(1L, "Александр", "Пушкин");
        Genre genre = new Genre(1L, "Поэма");
        return new Book(2L, "Евгений Онегин", 1833, author, genre, null);
    }

    private Book expectedBookAfterUpdate() {
        Author author = new Author(1L, "Александр", "Пушкин");
        Genre genre = new Genre(1L, "Поэма");
        return new Book(1L, "Евгений Онегин", 1833, author, genre, null);
    }
}
