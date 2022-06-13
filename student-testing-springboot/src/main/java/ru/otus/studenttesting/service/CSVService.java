package ru.otus.studenttesting.service;

import ru.otus.studenttesting.domain.Question;

import java.util.List;

public interface CSVService {

    List<Question> getAllFromCsv();

}
