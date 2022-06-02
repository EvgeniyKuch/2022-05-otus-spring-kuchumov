package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Question {

    /**
     * Содержимое вопроса
     */
    private String description;

    /**
     * Варианты ответа
     */
    private List<Answer> answers;

}
