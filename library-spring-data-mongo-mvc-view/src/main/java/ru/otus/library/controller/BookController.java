package ru.otus.library.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.library.domain.Book;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;

import java.util.List;

@AllArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/book")
    public String allBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "listBook";
    }

    @PostMapping("/book")
    public String saveBook(Book book) {
        bookService.save(book);
        return "redirect:/book";
    }

    @GetMapping("/book/{id}")
    public String getBookById(@PathVariable("id") String id, Model model) {
        Book book = bookService.getBookWithCommentsById(id);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/book/edit/{id}")
    public String bookById(@PathVariable("id") String id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("genres", genreService.getAllGenres());
        return "editBook";
    }

    @GetMapping("book/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("genres", genreService.getAllGenres());
        return "addBook";
    }

    @GetMapping("book/delete/{id}")
    public String deleteBook(@PathVariable("id") String id) {
        bookService.deleteById(id);
        return "redirect:/book";
    }
}
