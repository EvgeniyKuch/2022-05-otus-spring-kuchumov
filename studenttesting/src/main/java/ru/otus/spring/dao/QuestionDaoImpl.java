package ru.otus.spring.dao;

import org.apache.commons.csv.CSVFormat;
import org.springframework.core.io.Resource;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {

    private final Resource resource;

    private final List<Question> questions = new ArrayList<>();

    public QuestionDaoImpl(Resource resource) {
        this.resource = resource;
    }

    public void init() throws IOException {
        CSVFormat.newFormat(';').parse(new FileReader(resource.getFile())).forEach(record -> {
            List<String> csvRecords = record.toList();
            Question question = new Question();
            question.setDescription(csvRecords.get(0));
            question.setAnswers(parseAnswers(csvRecords));
            questions.add(question);
        });
    }

    private List<Answer> parseAnswers(List<String> csvRecords) {
        List<Answer> answers = new ArrayList<>();
        for (int i = 1; i < csvRecords.size(); i++) {
            String csvRecord = csvRecords.get(i);
            if (!csvRecord.isEmpty()) {
                Boolean correct = csvRecord.endsWith("*");
                Answer answer = new Answer();
                answer.setDescription(!correct ? csvRecord : csvRecord.substring(0, csvRecord.length() - 1));
                answer.setCorrect(correct);
                answers.add(answer);
            }
        }
        return answers;
    }

    @Override
    public List<Question> findAll() {
        return this.questions;
    }
}
