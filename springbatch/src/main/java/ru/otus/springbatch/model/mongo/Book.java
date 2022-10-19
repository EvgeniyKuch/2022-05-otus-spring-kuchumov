package ru.otus.springbatch.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Book {

    @Id
    private String id;

    private String name;

    private Integer year;

    @DBRef
    private Author author;

    @DBRef
    private Genre genre;
}
