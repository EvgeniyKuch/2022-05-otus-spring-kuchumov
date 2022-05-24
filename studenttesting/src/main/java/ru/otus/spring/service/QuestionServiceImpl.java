package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getAll() {
        return this.questionDao.findAll();
    }

    @Override
    public String print(List<Question> questions) {
        AtomicInteger numberAnswer = new AtomicInteger(1);
        return questions.stream().map(question -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(question.getDescription()).append(System.lineSeparator());
            question.getAnswers().forEach(answer -> stringBuilder.append("\t")
                    .append(numberAnswer.getAndIncrement()).append(". ")
                    .append(answer.getDescription()).append(System.lineSeparator()));
            numberAnswer.set(1);
            return stringBuilder.toString();
        }).collect(Collectors.joining());
    }
}
