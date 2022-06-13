package ru.otus.studenttesting.service;

import ru.otus.studenttesting.domain.User;

import java.util.concurrent.atomic.AtomicInteger;

public interface TestingService {

    void testing();

    void resultsProcess(AtomicInteger numberCorrectAnswers, User user);

    String inputProcess(String answer, AtomicInteger questionNumber, AtomicInteger numberCorrectAnswers);
}
