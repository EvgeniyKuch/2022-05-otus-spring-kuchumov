package ru.otus.studenttesting.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.otus.studenttesting.domain.Question;
import ru.otus.studenttesting.service.Loader;
import ru.otus.studenttesting.service.Parser;

import java.util.List;

@Service
public class QuestionDaoCSV implements QuestionDao {

    private final Loader loaderCSV;

    private final Parser parserCSV;

    public QuestionDaoCSV(Loader loaderCSV, Parser parserCSV) {
        this.loaderCSV = loaderCSV;
        this.parserCSV = parserCSV;
    }

    @Override
    @Cacheable("questions")
    public List<Question> findAll() {
        return parserCSV.parse(loaderCSV.loadData());
    }
}
