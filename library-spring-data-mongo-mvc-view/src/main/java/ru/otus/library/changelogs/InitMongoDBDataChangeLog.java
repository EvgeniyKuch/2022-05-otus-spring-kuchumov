package ru.otus.library.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private List<Author> authors = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    @ChangeSet(order = "000", id = "dropDB", author = "kuchumov", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "kuchumov", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        authors.add(repository.save(new Author(null, "Александр", "Пушкин")));
        authors.add(repository.save(new Author(null, "Лев", "Толстой")));
        authors.add(repository.save(new Author(null, "Виктор", "Пелевин")));
        authors.add(repository.save(new Author(null, "Джоан", "Роулинг")));
        authors.add(repository.save(new Author(null, "Антон", "Чехов")));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "kuchumov", runAlways = true)
    public void initGenres(GenreRepository repository) {
        genres.add(repository.save(new Genre(null, "Комедия")));
        genres.add(repository.save(new Genre(null, "Трагедия")));
        genres.add(repository.save(new Genre(null, "Драма")));
        genres.add(repository.save(new Genre(null, "Поэма")));
        genres.add(repository.save(new Genre(null, "Рассказ")));
        genres.add(repository.save(new Genre(null, "Фантастика")));
        genres.add(repository.save(new Genre(null, "Роман")));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "kuchumov", runAlways = true)
    public void initBooks(BookRepository repository) {
        books.add(repository.save(new Book(null, "Капитанская дочка", 1836, authors.get(0), genres.get(6), null)));
        books.add(repository.save(new Book(null, "Война и мир", 1873, authors.get(1), genres.get(6), null)));
        books.add(repository.save(new Book(null, "Гарри Поттер и философский камень", 2001, authors.get(3), genres.get(5), null)));
    }

    @ChangeSet(order = "004", id = "initComments", author = "kuchumov", runAlways = true)
    public void initComments(MongoDatabase db, CommentRepository commentRepository, BookRepository bookRepository) {
        initComments(commentRepository, 0, "Не понравилось", "Книга вкусно пахнет");
        initComments(commentRepository, 1, "Много букв", "Понравилось");
        initComments(commentRepository, 2, "Интересно", "Захватывающе", "Закрученный сюжет");
    }

    private void initComments(CommentRepository commentRepository,
                              int bookIndex, String... contents) {
        Book book = books.get(bookIndex);
        Stream.of(contents).forEach(content -> commentRepository.save(new Comment(null, book, content)));
    }
}
