package ru.otus.library.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

import java.util.List;
import java.util.Map;

@Repository
public class AuthorDAOImpl implements AuthorDAO {

    private final NamedParameterJdbcOperations jdbc;

    private final RowMapper<Author> authorRowMapper = (rs, rowNum) -> new Author(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"));

    public AuthorDAOImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Author> getAllAuthors() {
        return jdbc.query("SELECT id, first_name, last_name FROM library.authors", authorRowMapper);
    }

    @Override
    public boolean checkExistId(Long id) {
        Boolean check = jdbc.queryForObject("SELECT count(id) = 1 FROM library.authors WHERE id = :id", Map.of("id", id), Boolean.class);
        return check != null ? check : false;
    }
}
