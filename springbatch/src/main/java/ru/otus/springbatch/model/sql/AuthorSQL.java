package ru.otus.springbatch.model.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorSQL {

    private Long id;

    private String firstName;

    private String lastName;

}
