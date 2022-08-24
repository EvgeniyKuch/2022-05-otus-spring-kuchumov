package ru.otus.library.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.library.domain.Comment;
import ru.otus.library.service.BookService;
import ru.otus.library.service.CommentService;

@Controller
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    @GetMapping("/comment/add-for-book/{id}")
    public String addComment(@PathVariable("id") String bookId, Model model) {
        Comment comment = new Comment();
        comment.setBook(bookService.getBookById(bookId));
        model.addAttribute("comment", comment);
        return "addComment";
    }

    @GetMapping("/comment/edit/{id}")
    public String editComment(@PathVariable("id") String id, Model model) {
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "editComment";
    }

    @PostMapping("/comment/save")
    public String saveComment(Comment comment) {
        commentService.save(comment);
        return "redirect:/book/" + comment.getBook().getId();
    }

    @GetMapping("/comment/delete/{commentId}/{bookId}")
    public String deleteComment(@PathVariable("commentId") String commentId, @PathVariable("bookId") String bookId) {
        commentService.deleteCommentById(commentId);
        return "redirect:/book/" + bookId;
    }
}
