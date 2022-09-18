package ru.otus.library.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;
import ru.otus.library.error.exception.NotFoundException;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.CommentDTO;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.mapper.BookMapper;
import ru.otus.library.mapper.CommentMapper;

@AllArgsConstructor
@Controller
public class CommentController {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @GetMapping("/comment/add-for-book/{id}")
    public String addComment(@PathVariable("id") String bookId, Model model) {
        model.addAttribute("comment", bookRepository.findById(bookId)
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
                        }))
                .map(bookDTO -> new CommentDTO(null, bookDTO, null))
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("Книга с id = %s не найдена", bookId))))
        );
        return "editComment";
    }

    @GetMapping("/comment/edit/{id}")
    public String editComment(@PathVariable("id") String id, Model model) {
        model.addAttribute("comment", commentRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("Комментарий с id = %s не найден", id))))
                .map(comment -> new CommentMapper().toDTO(comment))
                .flatMap(commentDTO -> bookRepository.findById(commentDTO.getBookDTO().getId())
                        .switchIfEmpty(Mono.error(new NotFoundException(String.format("Книга с id = %s не найдена",
                                commentDTO.getBookDTO().getId()))))
                        .map(book -> {
                            commentDTO.getBookDTO().setYear(book.getYear());
                            commentDTO.getBookDTO().setName(book.getName());
                            commentDTO.getBookDTO().getAuthor().setId(book.getAuthor());
                            commentDTO.getBookDTO().getGenre().setId(book.getGenre());
                            return commentDTO;
                        }))
                .flatMap(commentDTO -> authorRepository.findById(commentDTO.getBookDTO().getAuthor().getId())
                        .map(author -> {
                            commentDTO.getBookDTO().setAuthor(author);
                            return commentDTO;
                        }))
                .flatMap(commentDTO -> genreRepository.findById(commentDTO.getBookDTO().getGenre().getId())
                        .map(genre -> {
                            commentDTO.getBookDTO().setGenre(genre);
                            return commentDTO;
                        }))

        );
        return "editComment";
    }

    @PostMapping("/comment/save")
    public String saveComment(Comment comment, Model model) {
        model.addAttribute(commentRepository.save(comment));
        return "redirect:/book/" + comment.getBook();
    }

    @GetMapping("/comment/delete/{commentId}/{bookId}")
    public String deleteComment(@PathVariable("commentId") String commentId, @PathVariable("bookId") String bookId, Model model) {
        model.addAttribute(commentRepository.deleteById(commentId));
        return "redirect:/book/" + bookId;
    }
}
