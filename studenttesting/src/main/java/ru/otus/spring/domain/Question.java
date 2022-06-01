package ru.otus.spring.domain;

import lombok.Data;

import java.util.List;

@Data
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
