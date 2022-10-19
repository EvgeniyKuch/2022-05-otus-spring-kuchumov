package ru.otus.springbatch.model.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSQL {

    private Long id;

    private Long book;

    private String content;

}
