package ru.otus.studenttesting.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.studenttesting.domain.User;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TestingServiceImpl implements TestingService {

    private final PrintService printService;

    private final QuestionService questionService;

    private final UserService userService;

    private final IOService ioService;

    private final MessageService messageService;

    private final int threshold;

    public TestingServiceImpl(PrintService printService,
                              QuestionService questionService,
                              UserService userService,
                              IOService ioService,
                              MessageService messageService, @Value("${threshold}") int threshold) {
        this.printService = printService;
        this.questionService = questionService;
        this.userService = userService;
        this.ioService = ioService;
        this.messageService = messageService;
        this.threshold = threshold;
    }

    @Override
    public void testing() {
        var numberCorrectAnswers = new AtomicInteger(0);
        var user = createUser();
        testingProcess(numberCorrectAnswers);
        resultsProcess(numberCorrectAnswers, user);
    }

    @Override
    public void resultsProcess(AtomicInteger numberCorrectAnswers, User user) {
        user.setGrade(numberCorrectAnswers.get() >= threshold);
        user = userService.saveUser(user);
        outln(getResultString(numberCorrectAnswers, user));
    }

    @Override
    public String inputProcess(String answer, AtomicInteger questionNumber, AtomicInteger numberCorrectAnswers) {
        if (!isOneDigitNumber(answer)) {
            questionNumber.decrementAndGet();
            return msg("input.error");
        }
        if (checkIndexOutOfBound(Integer.parseInt(answer), questionNumber)) {
            questionNumber.decrementAndGet();
            return msg("out.of.bound");
        }
        if (correctAnswer(Integer.parseInt(answer), questionNumber)) {
            numberCorrectAnswers.incrementAndGet();
            return msg("correct");
        } else {
            return msg("incorrect");
        }
    }

    private boolean correctAnswer(int variant, AtomicInteger questionNumber) {
        return variant == questionService.getCorrectAnswerIndexForQuestion(questionNumber.get()) + 1;
    }

    private boolean checkIndexOutOfBound(int variant, AtomicInteger questionNumber) {
        return variant < 1 || variant > questionService.getNumberAnswersForQuestion(questionNumber.get());
    }

    private boolean isOneDigitNumber(String string) {
        return string.matches("\\d");
    }

    private void testingProcess(AtomicInteger numberCorrectAnswers) {
        for (AtomicInteger i = new AtomicInteger(0);
             i.get() < questionService.getNumberQuestions();
             i.getAndIncrement()) {
            printService.printQuestion(i.get());
            outln(inputProcess(in(), i, numberCorrectAnswers));
        }
    }

    private String getResultString(AtomicInteger numberCorrectAnswers, User user) {
        return new StringBuilder()
                .append(msg("number.correct.answers"))
                .append(numberCorrectAnswers).append(System.lineSeparator())
                .append(userService.resultById(user.getId())).toString();
    }

    private User createUser() {
        User user = userService.getNewUser();
        user.setFirstName(query("first.name"));
        user.setLastName(query("last.name"));
        return user;
    }

    private String query(String query) {
        out(msg("enter"));
        outln(msg(query));
        return in();
    }

    private void out(String out) {
        ioService.output(out);
    }

    private void outln(String out) {
        ioService.outputln(out);
    }

    private String in() {
        return ioService.input();
    }

    private String msg(String code) {
        return messageService.getMessage(code);
    }
}
