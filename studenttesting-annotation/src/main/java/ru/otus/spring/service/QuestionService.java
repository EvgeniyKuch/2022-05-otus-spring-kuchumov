package ru.otus.spring.service;

import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getAll();

    Question get(int i);

    int getCorrectAnswerIndexForQuestion(int i);

    int getNumberAnswersForQuestion(int i);

    int getNumberQuestions();

    List<Answer> getAnswers(int i);
}
