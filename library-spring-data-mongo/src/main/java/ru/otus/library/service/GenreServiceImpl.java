package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreRepository;

import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public String getAllGenres() {
        return genreRepository.findAll().stream().map(this::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    private String toString(Genre genre) {
        return String.format("Id: %s\t%s", genre.getId(), genre.getName());
    }
}
