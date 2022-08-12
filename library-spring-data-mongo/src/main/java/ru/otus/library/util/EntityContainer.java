package ru.otus.library.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

@Getter
@Setter
@NoArgsConstructor
public class EntityContainer {
    private Long id;
    private Book book;
    private Integer year;
    private Author author;
    private Genre genre;
    private Comment comment;
    private String error;
}
