package ru.otus.library.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.error.exception.NotFoundException;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookDTO;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.mapper.BookMapper;

@AllArgsConstructor
@Controller
public class BookController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    @GetMapping("/book")
    public String allBooks() {
        return "listBook";
    }

    @GetMapping("/allbook")
    @ResponseBody
    public Flux<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .map(book -> new BookMapper().toDTO(book))
                .flatMap(bookDTO -> authorRepository.findById(bookDTO.getAuthor().getId())
                        .defaultIfEmpty(new Author())
                        .map(author -> {
                            bookDTO.setAuthor(author);
                            return bookDTO;
                        }))
                .flatMap(bookDTO -> genreRepository.findById(bookDTO.getGenre().getId())
                        .defaultIfEmpty(new Genre())
                        .map(genre -> {
                            bookDTO.setGenre(genre);
                            return bookDTO;
                        }));
    }

    @PostMapping("/book")
    public String saveBook(Book book, Model model) {
        model.addAttribute(bookRepository.save(book));
        return "redirect:/book";
    }

    @GetMapping("/book/{id}")
    public String getBookById(@PathVariable("id") String id, Model model) {
        model.addAttribute("book", bookRepository.findById(id)
                .map(book -> new BookMapper().toDTO(book))
                .flatMap(bookDTO -> authorRepository.findById(bookDTO.getAuthor().getId())
                        .defaultIfEmpty(new Author())
                        .map(author -> {
                            bookDTO.setAuthor(author);
                            return bookDTO;
                        }))
                .flatMap(bookDTO -> genreRepository.findById(bookDTO.getGenre().getId())
                        .defaultIfEmpty(new Genre())
                        .map(genre -> {
                            bookDTO.setGenre(genre);
                            return bookDTO;
                        })));
        model.addAttribute("comments", commentRepository.findAllByBook(id));
        return "book";
    }

    @GetMapping("/book/edit/{id}")
    public String bookById(@PathVariable("id") String id, Model model) {
        model.addAttribute("book", bookRepository.findById(id).map(book -> new BookMapper().toDTO(book))
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("Книга с id = %s не найдена", id)))));
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "editBook";
    }

    @GetMapping("book/add")
    public String addBook(Model model) {
        model.addAttribute("book", new BookMapper().toDTO(new Book()));
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "editBook";
    }


    @GetMapping("book/delete/{id}")
    public String deleteBook(@PathVariable("id") String id, Model model) {
        model.addAttribute(bookRepository.deleteById(id));
        model.addAttribute(commentRepository.deleteAllByBook(id));
        return "redirect:/book";
    }
}
