package ru.otus.library.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import ru.otus.library.error.exception.NotFoundException;
import ru.otus.library.repository.AuthorRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @HystrixCommand
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @HystrixCommand
    @Override
    public Author findById(String id) {
        return authorRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Автор с id = %s не найден", id)));
    }

    @HystrixCommand
    @Override
    @Secured({"ROLE_ADMIN"})
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @HystrixCommand
    @Override
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }
}
