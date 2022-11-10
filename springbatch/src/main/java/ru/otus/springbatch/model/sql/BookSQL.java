package ru.otus.springbatch.model.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSQL {

    private Long id;

    private String name;

    private Integer year;

    private Long author;

    private Long genre;
}
