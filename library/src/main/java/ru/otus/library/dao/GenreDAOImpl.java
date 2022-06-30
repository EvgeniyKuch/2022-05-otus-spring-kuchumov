package ru.otus.library.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Map;

@Repository
public class GenreDAOImpl implements GenreDAO {

    private final NamedParameterJdbcOperations jdbc;

    private final RowMapper<Genre> genreRowMapper = (rs, rowNum) -> new Genre(
            rs.getLong("id"),
            rs.getString("name"));

    public GenreDAOImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Genre> getAllGenres() {
        return jdbc.query("SELECT id, name FROM library.genres", genreRowMapper);
    }

    @Override
    public boolean checkExistId(Long id) {
        Boolean check = jdbc.queryForObject("SELECT count(id) = 1 FROM library.genres WHERE id = :id", Map.of("id", id), Boolean.class);
        return check != null ? check : false;
    }
}
