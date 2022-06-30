package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.domain.Author;

import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO;

    public AuthorServiceImpl(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public String getAllAuthors() {
        return authorDAO.getAllAuthors().stream().map(this::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    private String toString(Author author) {
        return String.format("Id: %d\t%s %s", author.getId(), author.getFirstName(), author.getLastName());
    }
}
