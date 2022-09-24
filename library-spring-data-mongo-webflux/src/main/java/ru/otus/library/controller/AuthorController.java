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
import ru.otus.library.repository.AuthorRepository;

@AllArgsConstructor
@Controller
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping("/author")
    public String allAuthors(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        return "listAuthor";
    }

    @GetMapping("/author/edit/{id}")
    public String editAuthor(@PathVariable("id") String id, Model model) {
        model.addAttribute("author", authorRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("Автор с id = %s не найден", id)))));
        return "editAuthor";
    }

    @GetMapping("/author/add")
    public String addAuthor(Model model) {
        model.addAttribute("author", Mono.just(new Author()));
        return "editAuthor";
    }

    @PostMapping("/author")
    public String saveAuthor(Author author, Model model) {
        model.addAttribute(authorRepository.save(author));
        return "redirect:/author";
    }

    @GetMapping("/author/delete/{id}")
    public String deleteAuthor(@PathVariable("id") String id, Model model) {
        model.addAttribute(authorRepository.deleteById(id));
        return "redirect:/author";
    }
}
