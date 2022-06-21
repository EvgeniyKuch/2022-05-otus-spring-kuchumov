package ru.otus.studenttesting.dao;

import org.springframework.stereotype.Service;
import ru.otus.studenttesting.domain.Question;
import ru.otus.studenttesting.service.CSVService;

import java.util.List;

@Service
public class QuestionDaoImpl implements QuestionDao {

    private final CSVService csvService;

    public QuestionDaoImpl(CSVService csvService) {
        this.csvService = csvService;
    }

    @Override
    public List<Question> findAll() {
        return this.csvService.getAllFromCsv();
    }
}
