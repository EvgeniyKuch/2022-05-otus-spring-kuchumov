package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorRepository;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final CheckService checkService;

    @Override
    public String getAllAuthors() {
        return authorRepository.findAll().stream().map(this::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String insertAuthor(String firstName, String lastName) {
        return toString(authorRepository.save(new Author(null, firstName, lastName)));
    }

    @Override
    @Transactional
    public String updateAuthor(String authorId, String firstName, String lastName) {
        var container = checkService.checkAndGetEntities(null, null, authorId, null, null);
        if (container.getError() != null) {
            return container.getError();
        }
        Author author = container.getAuthor();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author = authorRepository.save(author);
        return String.format("Добавлен автор:%s%s", System.lineSeparator(), toString(author));
    }

    @Override
    public String deleteById(String id) {
        authorRepository.deleteById(id);
        return String.format("Автор id=%s удален", id);
    }

    private String toString(Author author) {
        return String.format("Id: %s\t%s %s", author.getId(), author.getFirstName(), author.getLastName());
    }
}
