package ru.otus.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Genre;

import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDAO genreDAO;

    public GenreServiceImpl(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllGenres() {
        return genreDAO.findAll().stream().map(this::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    private String toString(Genre genre) {
        return String.format("Id: %d\t%s", genre.getId(), genre.getName());
    }
}
