package ru.otus.library.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;

@ShellComponent
public class ApplicationCommand {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    public ApplicationCommand(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @ShellMethod(value = "Показать все книги", key = {"ab", "allbooks"})
    public String getAllBooks() {
        return bookService.getAllBooks();
    }

    @ShellMethod(value = "Показать всех авторов", key = {"aa", "allauthors"})
    public String getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @ShellMethod(value = "Показать все жанры", key = {"ag", "allgenres"})
    public String getAllGenres() {
        return genreService.getAllGenres();
    }

    @ShellMethod(value = "Показать книгу по id. bi --id 3", key = {"bi", "bookid"})
    public String getBookById(@ShellOption({"--id"}) String id) {
        return bookService.getBookById(id);
    }

    @ShellMethod(value = "Добавить книгу в библиотеку. insertbook --name Каштанка --year 1887 --author 5 --genre 6. ib -N Каштанка -Y 1887 -A 5 -G 6",
            key = {"ib", "insertbook"})
    public String insertBook(@ShellOption({"-N", "--name"}) String name, @ShellOption({"-Y", "--year"}) String year,
                             @ShellOption({"-A", "--author"}) String authorId, @ShellOption({"-G", "--genre"}) String genreId) {
        return bookService.insertBook(name, year, authorId, genreId);
    }

    @ShellMethod(value = "Изменить книгу в библиотеке. updatebook --id 1 --name Каштанка --year 1887 --author 5 --genre 6. ub 1 -N Каштанка -Y 1887 -A 5 -G 6",
            key = {"ub", "updatebook"})
    public String updateBook(@ShellOption({"--id"}) String id,
                             @ShellOption({"-N", "--name"}) String name, @ShellOption({"-Y", "--year"}) String year,
                             @ShellOption({"-A", "--author"}) String authorId, @ShellOption({"-G", "--genre"}) String genreId) {
        return bookService.updateBook(id, name, year, authorId, genreId);
    }

    @ShellMethod(value = "Удалить книгу из бибилиотеки. deletebook --id 1. db 1",
            key = {"db", "deletebook"})
    public String deleteBookById(@ShellOption({"--id"}) String id) {
        return bookService.deleteById(id);
    }
}
