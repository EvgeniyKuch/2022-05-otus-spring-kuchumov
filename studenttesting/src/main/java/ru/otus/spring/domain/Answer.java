package ru.otus.spring.domain;

import lombok.Data;

/**
 * Класс ответа
 */
@Data
public class Answer {

    private Long id;

    /**
     * Содержимое ответа
     */
    private String description;

    /**
     * Верный ответ или нет
     */
    private Boolean correct;

}
