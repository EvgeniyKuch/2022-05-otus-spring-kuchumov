package ru.otus.library.mapper;

import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDTO;

public class CommentMapper {

    public CommentDTO toDTO(Comment comment) {
        return new CommentDTO(comment.getId(), new BookMapper().fromId(comment.getBook()), comment.getContent());
    }
}
