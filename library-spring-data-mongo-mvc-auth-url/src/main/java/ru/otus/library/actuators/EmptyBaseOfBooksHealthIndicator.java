package ru.otus.library.actuators;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.library.repository.BookRepository;

import java.util.Random;

@AllArgsConstructor
@Component
public class EmptyBaseOfBooksHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        long bookCount = bookRepository.count();
        if (bookCount != 0) {
            return Health.up().withDetail("message", String.format("В библиотеке %d книг", bookCount)).build();
        } else {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Внимание! Кто-то удалил все книги!")
                    .build();

        }
    }
}
