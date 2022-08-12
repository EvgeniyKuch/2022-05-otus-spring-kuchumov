package ru.otus.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorRepository;

import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public String getAllAuthors() {
        return authorRepository.findAll().stream().map(this::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    private String toString(Author author) {
        return String.format("Id: %s\t%s %s", author.getId(), author.getFirstName(), author.getLastName());
    }
}
