package ru.otus.studenttesting.service;

import org.springframework.stereotype.Service;
import ru.otus.studenttesting.domain.Answer;
import ru.otus.studenttesting.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ParserCSV implements Parser {

    @Override
    public List<Question> parse(String source) {
        List<Question> questions = new ArrayList<>();
        Stream.of(source.split("\\|")).forEach(line -> {
            String[] questionAndAnswers = line.split(";");
            if (questionAndAnswers.length > 0) {
                questions.add(new Question(questionAndAnswers[0], parseAnswers(questionAndAnswers)));
            }
        });
        return questions;
    }

    private List<Answer> parseAnswers(String[] questionAndAnswers) {
        List<Answer> answers = new ArrayList<>();
        for (int i = 1; i < questionAndAnswers.length; i++) {
            String answer = questionAndAnswers[i];
            if (!answer.isEmpty()) {
                Boolean correct = answer.endsWith("*");
                String description = !correct ? answer : answer.substring(0, answer.length() - 1);
                answers.add(new Answer(description, correct));
            }
        }
        return answers;
    }
}
