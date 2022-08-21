package ru.otus.library.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.CommentService;
import ru.otus.library.service.GenreService;

@AllArgsConstructor
@ShellComponent
public class ApplicationCommand {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

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

    @ShellMethod(value = "Показать книгу с комментариями по id книги. bi --id 3", key = {"bi", "bookid"})
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

    @ShellMethod(value = "Показать все комментарии", key = {"ac", "allcomments"})
    public String getAllComments() {
        return commentService.getAllComments();
    }

    @ShellMethod(value = "Показать комментарий по его id. ci --id 3", key = {"ci", "commentid"})
    public String getCommentById(@ShellOption({"--id"}) String id) {
        return commentService.getCommentById(id);
    }

    @ShellMethod(value = "Показать комментарии к книге по её id. cbi --id 3", key = {"cbi", "commbookid"})
    public String getAllCommentsByBookId(@ShellOption({"--id"}) String id) {
        return commentService.getAllCommentsByBookId(id);
    }

    @ShellMethod(value = "Добавить комментарий к книге. insertcomm --bookid 2 --content \"Понравилось\". ic -I 2 -C \"Понравилось\"",
            key = {"ic", "insertcomm"})
    public String insertComment(@ShellOption({"-I", "--bookid"}) String bookId, @ShellOption({"-C", "--content"}) String content) {
        return commentService.addNewComment(bookId, content);
    }

    @ShellMethod(value = "Изменить текст комментария по его id. updatecomm --id 2 --content \"Не понравилось\". uc -I 2 -C \"Не понравилось\"",
            key = {"uc", "updatecomm"})
    public String updateComment(@ShellOption({"-I", "--id"}) String bookId, @ShellOption({"-C", "--content"}) String content) {
        return commentService.updateContentComment(bookId, content);
    }

    @ShellMethod(value = "Удалить комментарий к книге по его id. deletecomm --id 1. dc 1",
            key = {"dc", "deletecomm"})
    public String deleteCommentById(@ShellOption({"--id"}) String id) {
        return commentService.deleteCommentById(id);
    }

    @ShellMethod(value = "Добавить автора в бибилиотеку. insertauthor --firstname \"Иван\" --lastname \"Тургенев\". ia -F \"Иван\" -L \"Тургенев\"",
            key = {"ia", "insertauthor"})
    public String insertAuthor(@ShellOption({"-F", "--firstname"}) String firstname, @ShellOption({"-L", "--lastname"}) String lastname) {
        return authorService.insertAuthor(firstname, lastname);
    }

    @ShellMethod(value = "Изменить автора по его id. updateauthor --id 2 --firstname \"Иван\" --lastname \"Тургенев\". ua -I 2 --F \"Иван\" --L \"Тургенев\"",
            key = {"ua", "updateauthor"})
    public String updateComment(@ShellOption({"-I", "--id"}) String authorId, @ShellOption({"-F", "--firstname"}) String firstname, @ShellOption({"-L", "--lastname"}) String lastname) {
        return authorService.updateAuthor(authorId, firstname, lastname);
    }

    @ShellMethod(value = "Удалить автора по его id. deleteauthor --id 1. da 1",
            key = {"da", "deleteauthor"})
    public String deleteAuthorById(@ShellOption({"--id"}) String id) {
        return authorService.deleteById(id);
    }
}
