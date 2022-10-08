package ru.otus.library.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.domain.*;
import ru.otus.library.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        books.add(repository.save(new Book(null, "Капитанская дочка", 1836, authors.get(0), genres.get(6))));
        books.add(repository.save(new Book(null, "Война и мир", 1873, authors.get(1), genres.get(6))));
        books.add(repository.save(new Book(null, "Гарри Поттер и философский камень", 2001, authors.get(3), genres.get(5))));
    }

    @ChangeSet(order = "004", id = "initComments", author = "kuchumov", runAlways = true)
    public void initComments(CommentRepository commentRepository) {
        initComments(commentRepository, 0, "Не понравилось", "Книга вкусно пахнет");
        initComments(commentRepository, 1, "Много букв", "Понравилось");
        initComments(commentRepository, 2, "Интересно", "Захватывающе", "Закрученный сюжет");
    }

    @ChangeSet(order = "005", id = "initUsers", author = "kuchumov", runAlways = true)
    public void initUsers(UserRepository userRepository) {
        userRepository.save(new User(null, "admin", // login: admin password: admin
                "$2a$10$uVBkn5o6w0ee6G5ZM8aXuOscoZSsQYkawqDe0kHiIKHfLJ3b9NzZu", Set.of("ROLE_ADMIN")));
        userRepository.save(new User(null, "user", // login: user password: user
                "$2a$10$50oNNb35sKb85Tqx27XS7OOe0Rg3JzMsMv87yv3wcrQ9Q8dq5Fa5G", Set.of("ROLE_USER")));
    }

    private void initComments(CommentRepository commentRepository,
                              int bookIndex, String... contents) {
        Book book = books.get(bookIndex);
        Stream.of(contents).forEach(content -> commentRepository.save(new Comment(null, book, content)));
    }
}
