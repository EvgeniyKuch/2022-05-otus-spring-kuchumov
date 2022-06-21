package ru.otus.studenttesting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.studenttesting.dao.QuestionDao;
import ru.otus.studenttesting.domain.Answer;
import ru.otus.studenttesting.domain.Question;
import ru.otus.studenttesting.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {TestingServiceImpl.class, QuestionServiceImpl.class})
class TestingServiceImplTest {

    @MockBean
    PrintService printService;

    @MockBean
    IOService ioService;

    @MockBean
    UserService userService;

    @MockBean
    QuestionDao questionDao;

    @MockBean
    MessageService messageService;

    @Autowired
    TestingService testingService;

    @BeforeEach
    void setUp() {
        given(messageService.getMessage(anyString())).will(invocation -> invocation.getArguments()[0]);
        given(userService.getNewUser()).willReturn(new User());
        given(userService.saveUser(any())).will(invocation -> invocation.getArguments()[0]);
        given(questionDao.findAll()).willReturn(allQuestions());
    }

    @Test
    void whenUserInputCorrectThreeAnswerThenGradePassTesting() {
        given(ioService.input())
                .willReturn("Ivan")
                .willReturn("Ivanov")
                .willReturn("3")
                .willReturn("4")
                .willReturn("1");
        testingService.testing();
        verify(userService, times(1)).saveUser(new User(null, "Ivan", "Ivanov", true));
    }

    @Test
    void whenUserInputCorrectOnlyOneAnswerThenGradeFailTesting() {
        given(ioService.input())
                .willReturn("Ivan")
                .willReturn("Ivanov")
                .willReturn("3")
                .willReturn("2")
                .willReturn("2");
        testingService.testing();
        verify(userService, times(1)).saveUser(new User(null, "Ivan", "Ivanov", false));
    }

    @Test
    void whenCorrectsAnswersLessTwoThenFailTest() {
        var user = new User();
        testingService.resultsProcess(new AtomicInteger(1), user);
        assertThat(user.getGrade()).isFalse();
    }

    @Test
    void whenCorrectsAnswersEqualsTwoThenPassTest() {
        var user = new User();
        testingService.resultsProcess(new AtomicInteger(2), user);
        assertThat(user.getGrade()).isTrue();
    }

    @Test
    void whenInputNotNumberThenReturnNumberInputErrorAndDecrementCycleCounter() {
        var numberCorrectAnswers = new AtomicInteger(0);
        var numberQuestion = new AtomicInteger(2);
        assertThat(testingService.inputProcess("qwerty", numberQuestion, numberCorrectAnswers))
                .isEqualTo("input.error");
        assertThat(numberCorrectAnswers.get()).isEqualTo(0);
        assertThat(numberQuestion.get()).isEqualTo(1);
    }

    @Test
    void whenInputNumberOutOfBoundThenReturnNumberOutOfBoundAndDecrementCycleCounter() {
        var numberCorrectAnswers = new AtomicInteger(0);
        var numberQuestion = new AtomicInteger(2);
        assertThat(testingService.inputProcess("8", numberQuestion, numberCorrectAnswers))
                .isEqualTo("out.of.bound");
        assertThat(numberCorrectAnswers.get()).isEqualTo(0);
        assertThat(numberQuestion.get()).isEqualTo(1);
    }

    @Test
    void whenInputIncorrectVariantThenReturnIncorrect() {
        var numberCorrectAnswers = new AtomicInteger(0);
        var numberQuestion = new AtomicInteger(2);
        assertThat(testingService.inputProcess("2", numberQuestion, numberCorrectAnswers))
                .isEqualTo("incorrect");
        assertThat(numberCorrectAnswers.get()).isEqualTo(0);
        assertThat(numberQuestion.get()).isEqualTo(2);
    }

    @Test
    void whenInputCorrectVariantThenReturnCorrectAndIncrementNumberCorrectAnswers() {
        var numberCorrectAnswers = new AtomicInteger(0);
        var numberQuestion = new AtomicInteger(2);
        assertThat(testingService.inputProcess("1", numberQuestion, numberCorrectAnswers))
                .isEqualTo("correct");
        assertThat(numberCorrectAnswers.get()).isEqualTo(1);
        assertThat(numberQuestion.get()).isEqualTo(2);
    }

    private List<Question> allQuestions() {
        List<Question> allQuestions = new ArrayList<>();
        Question question = new Question("How many hours in a day",
                List.of(new Answer("5", false),
                        new Answer("25", false),
                        new Answer("24", true),
                        new Answer("32", false),
                        new Answer("48", false)));
        allQuestions.add(question);
        question = new Question("How many days are in a non-leap year?",
                List.of(new Answer("100", false),
                        new Answer("202", false),
                        new Answer("150", false),
                        new Answer("365", true),
                        new Answer("25", false)));
        allQuestions.add(question);
        question = new Question("How many days in one week?",
                List.of(new Answer("7", true),
                        new Answer("14", false),
                        new Answer("100", false),
                        new Answer("20", false),
                        new Answer("36", false)));
        allQuestions.add(question);
        return allQuestions;
    }
}
