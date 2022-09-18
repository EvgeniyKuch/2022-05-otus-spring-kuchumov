package ru.otus.library.dto;

import lombok.*;
import org.springframework.context.annotation.Primary;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private String id;

    private String name;

    private Integer year;

    private Author author;

    private Genre genre;

}
