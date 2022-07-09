package ru.otus.library.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Map;

@Repository
public class BookDAOImpl implements BookDAO {

    private final NamedParameterJdbcOperations jdbc;

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Author author = new Author(
                rs.getLong("author_id"),
                rs.getString("author_first_name"),
                rs.getString("author_last_name"));
        Genre genre = new Genre(
                rs.getLong("genre_id"),
                rs.getString("genre_name"));
        return new Book(
                rs.getLong("book_id"),
                rs.getString("book_name"),
                rs.getInt("book_year"),
                author, genre);
    };

    public BookDAOImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long addBook(Book book) {
        SqlParameterSource parameters = new MapSqlParameterSource(Map.of(
                "name", book.getName(),
                "year", book.getYear(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()));
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("INSERT INTO `library`.`books`(`name`, `year`, `author_id`, `genre_id`) " +
                "VALUES(:name, :year, :author_id, :genre_id)", parameters, kh);
        return kh.getKey() != null ? kh.getKey().longValue() : 0L;
    }

    @Override
    public void updateBook(Book book) {
        SqlParameterSource parameters = new MapSqlParameterSource(Map.of(
                "id", book.getId(),
                "name", book.getName(),
                "year", book.getYear(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()));
        jdbc.update("UPDATE `library`.`books` " +
                        "SET `name` = :name, `year` = :year, `author_id` = :author_id, `genre_id` = :genre_id " +
                        "WHERE id = :id",
                parameters);
    }

    @Override
    public void deleteBookById(Long id) {
        jdbc.update("DELETE FROM `library`.`books` WHERE id = :id",
                new MapSqlParameterSource(Map.of("id", id)));
    }

    @Override
    public Book getBookById(Long id) {
        return jdbc.queryForObject(
                "SELECT b.`id` book_id, b.`name` book_name, b.`year` book_year, " +
                        "a.`id` author_id, a.`first_name` author_first_name, a.`last_name` author_last_name, " +
                        "g.`id` genre_id, g.`name` genre_name " +
                        "FROM `library`.`books` b LEFT JOIN `library`.`authors` a ON b.author_id = a.id " +
                        "LEFT JOIN `library`.`genres` g ON b.genre_id = g.id WHERE b.id = :book_id",
                Map.of("book_id", id), bookRowMapper);
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbc.query(
                "SELECT b.`id` book_id, b.`name` book_name, b.`year` book_year, " +
                        "a.`id` author_id, a.`first_name` author_first_name, a.`last_name` author_last_name, " +
                        "g.`id` genre_id, g.`name` genre_name " +
                        "FROM `library`.`books` b LEFT JOIN `library`.`authors` a ON b.author_id = a.id " +
                        "LEFT JOIN `library`.`genres` g ON b.genre_id = g.id", bookRowMapper);
    }

    @Override
    public boolean checkExistId(Long id) {
        Boolean check = jdbc.queryForObject("SELECT count(id) = 1 FROM `library`.`books` WHERE id = :id", Map.of("id", id), Boolean.class);
        return check != null ? check : false;
    }
}
