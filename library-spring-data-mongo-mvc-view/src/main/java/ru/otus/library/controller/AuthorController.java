package ru.otus.library.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.library.domain.Author;
import ru.otus.library.service.AuthorService;

@AllArgsConstructor
@Controller
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author")
    public String allAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "listAuthor";
    }

    @GetMapping("/author/edit/{id}")
    public String editAuthor(@PathVariable("id") String id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        return "editAuthor";
    }

    @GetMapping("/author/add")
    public String addAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "addAuthor";
    }

    @PostMapping("/author")
    public String saveAuthor(Author author) {
        authorService.save(author);
        return "redirect:/author";
    }

    @GetMapping("/author/delete/{id}")
    public String deleteAuthor(@PathVariable("id") String id) {
        authorService.deleteById(id);
        return "redirect:/author";
    }
}
