package ru.otus.spring.dao;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.CSVService;

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
