package ru.otus.library.error.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.library.error.exception.NotFoundException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(Model model, NotFoundException ex) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}
