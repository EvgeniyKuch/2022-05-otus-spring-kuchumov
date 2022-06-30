package ru.otus.studenttesting.service;

import ru.otus.studenttesting.domain.Answer;
import ru.otus.studenttesting.domain.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getAll();

    Question get(int i);

    int getCorrectAnswerIndexForQuestion(int i);

    int getNumberAnswersForQuestion(int i);

    int getNumberQuestions();

    List<Answer> getAnswers(int i);
}
