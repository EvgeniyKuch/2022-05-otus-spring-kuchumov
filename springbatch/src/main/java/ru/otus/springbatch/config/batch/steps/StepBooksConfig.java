package ru.otus.springbatch.config.batch.steps;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.springbatch.ProcessService;
import ru.otus.springbatch.model.mongo.Book;
import ru.otus.springbatch.model.sql.BookSQL;

import javax.sql.DataSource;

@Configuration
public class StepBooksConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MongoOperations template;

    @Autowired
    private ProcessService processService;

    @StepScope
    @Bean
    public JdbcCursorItemReader<BookSQL> booksReader() {
        return new JdbcCursorItemReaderBuilder<BookSQL>()
                .name("booksReader")
                .dataSource(dataSource)
                .sql("SELECT id, name, release_year, author_id, genre_id FROM books")
                .rowMapper((rs, rowNum) ->
                        new BookSQL(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getInt("release_year"),
                                rs.getLong("author_id"),
                                rs.getLong("genre_id")))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<BookSQL, Book> processorBooks() {
        return processService::mapBook;
    }

    @StepScope
    @Bean
    public MongoItemWriter<Book> booksWriter() {
        return new MongoItemWriterBuilder<Book>()
                .template(template)
                .collection("book")
                .build();
    }

    @Bean
    public Step migrationBooksStep(JdbcCursorItemReader<BookSQL> booksReader,
                                   MongoItemWriter<Book> booksWriter,
                                   ItemProcessor<BookSQL, Book> processorBooks) {
        return stepBuilderFactory.get("migrationBooksStep")
                .<BookSQL, Book>chunk(100)
                .reader(booksReader)
                .processor(processorBooks)
                .writer(booksWriter)
                .build();
    }

}
