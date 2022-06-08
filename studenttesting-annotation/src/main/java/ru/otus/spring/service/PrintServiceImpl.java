package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PrintServiceImpl implements PrintService {

    private final QuestionService questionService;

    public PrintServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public void printAllQuestions() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < questionService.getNumberQuestions(); i++) {
            stringBuilder.append(getQuestion(i));
        }
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void printQuestion(int i) {
        System.out.println(getQuestion(i).toString());
    }

    private StringBuilder getQuestion(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(questionService.get(i).getDescription()).append(System.lineSeparator());
        AtomicInteger numberAnswer = new AtomicInteger(1);
        questionService.getAnswers(i).forEach(answer -> stringBuilder.append("\t")
                .append(numberAnswer.getAndIncrement()).append(". ")
                .append(answer.getDescription()).append(System.lineSeparator()));
        return stringBuilder;
    }
}
