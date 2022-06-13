package ru.otus.studenttesting.dao;

import ru.otus.studenttesting.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
