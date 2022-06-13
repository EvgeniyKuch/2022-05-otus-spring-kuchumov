package ru.otus.studenttesting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.studenttesting.dao.QuestionDao;
import ru.otus.studenttesting.domain.Answer;
import ru.otus.studenttesting.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = QuestionServiceImpl.class)
class QuestionServiceImplTest {

    @MockBean
    private QuestionDao questionDAO;

    @Autowired
    private QuestionService questionService;

    private List<Question> allQuestions;

    @BeforeEach
    void setUp() {
        initListQuestions();
        given(questionDAO.findAll()).willReturn(allQuestions);
    }

    @Test
    void whenGetAllThenReturnAll() {
        assertThat(questionService.getAll()).isEqualTo(allQuestions);
    }

    @Test
    void whenGetEachThenReturnEach() {
        assertThat(questionService.get(1)).isEqualTo(allQuestions.get(1));
        assertThat(questionService.get(0)).isEqualTo(allQuestions.get(0));
    }

    @Test
    void shouldGetCorrectAnswerIndex() {
        assertThat(questionService.getCorrectAnswerIndexForQuestion(0)).isEqualTo(1);
        assertThat(questionService.getCorrectAnswerIndexForQuestion(1)).isEqualTo(2);
    }

    @Test
    void shouldGetNumberAnswersForQuestion() {
        assertThat(questionService.getNumberAnswersForQuestion(0)).isEqualTo(3);
        assertThat(questionService.getNumberAnswersForQuestion(1)).isEqualTo(4);
    }

    @Test
    void shouldGetNumberQuestions() {
        assertThat(questionService.getNumberQuestions()).isEqualTo(2);
    }

    @Test
    void shouldGetAnswersForOneQuestion() {
        assertThat(questionService.getAnswers(0)).isEqualTo(allQuestions.get(0).getAnswers());
        assertThat(questionService.getAnswers(1)).isEqualTo(allQuestions.get(1).getAnswers());
    }

    private void initListQuestions() {
        allQuestions = new ArrayList<>();
        Question question = new Question("How many hours in a day?",
                List.of(new Answer("5", false),
                        new Answer("24", true),
                        new Answer("7", false)));
        allQuestions.add(question);
        question = new Question("How many days in one week?",
                List.of(new Answer("100", false),
                        new Answer("14", false),
                        new Answer("7", true),
                        new Answer("20", false)));
        allQuestions.add(question);
    }
}
