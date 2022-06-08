package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс ответа
 */
@Data
@AllArgsConstructor
public class Answer {

    /**
     * Содержимое ответа
     */
    private String description;

    /**
     * Верный ответ или нет
     */
    private Boolean correct;

}
