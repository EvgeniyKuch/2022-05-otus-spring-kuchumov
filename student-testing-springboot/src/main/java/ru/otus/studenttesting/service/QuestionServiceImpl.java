package ru.otus.studenttesting.service;

import org.springframework.stereotype.Service;
import ru.otus.studenttesting.dao.QuestionDao;
import ru.otus.studenttesting.domain.Answer;
import ru.otus.studenttesting.domain.Question;

import java.util.List;

@Service
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
    public Question get(int i) {
        return getAll().get(i);
    }

    @Override
    public int getCorrectAnswerIndexForQuestion(int i) {
        List<Answer> answers = get(i).getAnswers();
        for (int j = 0; j < answers.size(); j++) {
            if (answers.get(j).getCorrect()) {
                return j;
            }
        }
        return -1;
    }

    @Override
    public int getNumberAnswersForQuestion(int i) {
        return get(i).getAnswers().size();
    }

    @Override
    public int getNumberQuestions() {
        return getAll().size();
    }

    @Override
    public List<Answer> getAnswers(int i) {
        return get(i).getAnswers();
    }
}
