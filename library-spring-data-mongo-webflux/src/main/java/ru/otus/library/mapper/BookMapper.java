package ru.otus.library.mapper;

import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookDTO;

public class BookMapper {

    public BookDTO toDTO(Book book) {
        Author author = new Author();
        author.setId(book.getAuthor());
        Genre genre = new Genre();
        genre.setId(book.getGenre());
        return new BookDTO(book.getId(), book.getName(), book.getYear(), author, genre);
    }

    public BookDTO fromId(String id) {
        Author author = new Author();
        Genre genre = new Genre();
        return new BookDTO(id, null, null, author, genre);
    }
}
