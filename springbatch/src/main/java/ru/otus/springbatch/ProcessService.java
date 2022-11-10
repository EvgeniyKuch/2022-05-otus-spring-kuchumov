package ru.otus.springbatch;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.springbatch.model.mongo.Author;
import ru.otus.springbatch.model.mongo.Book;
import ru.otus.springbatch.model.mongo.Comment;
import ru.otus.springbatch.model.mongo.Genre;
import ru.otus.springbatch.model.sql.AuthorSQL;
import ru.otus.springbatch.model.sql.BookSQL;
import ru.otus.springbatch.model.sql.CommentSQL;
import ru.otus.springbatch.model.sql.GenreSQL;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProcessService {

    /**
     * Кэши id-шников
     */
    private final Map<Long, String> idsAuthors = new HashMap<>();

    private final Map<Long, String> idsBooks = new HashMap<>();

    private final Map<Long, String> idsComments = new HashMap<>();

    private final Map<Long, String> idsGenres = new HashMap<>();

    public Genre mapGenre(GenreSQL genreSQL) {
        return new Genre(
                getId(genreSQL.getId(), idsGenres),
                genreSQL.getName());
    }

    public Author mapAuthor(AuthorSQL authorSQL) {
        return new Author(
                getId(authorSQL.getId(), idsAuthors),
                authorSQL.getFirstName(),
                authorSQL.getLastName());
    }

    public Book mapBook(BookSQL bookSQL) {
        return new Book(
                getId(bookSQL.getId(), idsBooks),
                bookSQL.getName(),
                bookSQL.getYear(),
                new Author(getId(bookSQL.getAuthor(), idsAuthors), null, null),
                new Genre(getId(bookSQL.getGenre(), idsGenres), null));
    }

    public Comment mapComment(CommentSQL commentSQL) {
        return new Comment(
                getId(commentSQL.getId(), idsComments),
                new Book(getId(commentSQL.getBook(), idsBooks), null, null, null, null),
                commentSQL.getContent()
        );
    }

    public void clearAllCaches() {
        idsAuthors.clear();
        idsBooks.clear();
        idsComments.clear();
        idsGenres.clear();
    }

    private String getId(Long id, Map<Long, String> cache) {
        String result = cache.get(id);
        if (result != null) {
            return result;
        }
        cache.put(id, new ObjectId().toString());
        return cache.get(id);
    }
}
