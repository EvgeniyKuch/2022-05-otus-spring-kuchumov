package ru.otus.library.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.library.error.exception.NotFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(Model model, NotFoundException ex) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}
