package ru.otus.studenttesting.service;

import org.apache.commons.csv.CSVFormat;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.otus.studenttesting.domain.Answer;
import ru.otus.studenttesting.domain.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVServiceImpl implements CSVService {

    private final MessageService messageService;

    public CSVServiceImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    @Cacheable("questions")
    public List<Question> getAllFromCsv() {
        List<Question> questions = new ArrayList<>();
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(messageService.getMessage("file.name"))) {
            if (inputStream != null) {
                try (var reader = new InputStreamReader(inputStream)) {
                    CSVFormat.newFormat(';').parse(reader).forEach(record -> {
                        List<String> csvRecords = record.toList();
                        questions.add(new Question(csvRecords.get(0), parseAnswers(csvRecords)));
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    private List<Answer> parseAnswers(List<String> csvRecords) {
        List<Answer> answers = new ArrayList<>();
        for (int i = 1; i < csvRecords.size(); i++) {
            String csvRecord = csvRecords.get(i);
            if (!csvRecord.isEmpty()) {
                Boolean correct = csvRecord.endsWith("*");
                String description = !correct ? csvRecord : csvRecord.substring(0, csvRecord.length() - 1);
                answers.add(new Answer(description, correct));
            }
        }
        return answers;
    }
}
