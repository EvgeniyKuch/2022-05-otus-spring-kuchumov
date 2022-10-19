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
import ru.otus.springbatch.model.mongo.Author;
import ru.otus.springbatch.model.sql.AuthorSQL;

import javax.sql.DataSource;

@Configuration
public class StepAuthorsConfig {

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
    public JdbcCursorItemReader<AuthorSQL> authorsReader() {
        return new JdbcCursorItemReaderBuilder<AuthorSQL>()
                .name("authorsReader")
                .dataSource(dataSource)
                .sql("SELECT id, first_name, last_name FROM authors")
                .rowMapper((rs, rowNum) ->
                        new AuthorSQL(
                                rs.getLong("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"))
                ).build();
    }

    @StepScope
    @Bean
    public ItemProcessor<AuthorSQL, Author> processorAuthors() {
        return processService::mapAuthor;
    }

    @StepScope
    @Bean
    public MongoItemWriter<Author> authorsWriter() {
        return new MongoItemWriterBuilder<Author>()
                .template(template)
                .collection("author")
                .build();
    }

    @Bean
    public Step migrationAuthorsStep(JdbcCursorItemReader<AuthorSQL> authorsReader,
                                     MongoItemWriter<Author> authorsWriter,
                                     ItemProcessor<AuthorSQL, Author> processorAuthors) {
        return stepBuilderFactory.get("migrationAuthorsStep")
                .<AuthorSQL, Author>chunk(100)
                .reader(authorsReader)
                .processor(processorAuthors)
                .writer(authorsWriter)
                .build();
    }

}
