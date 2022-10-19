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
import ru.otus.springbatch.model.mongo.Comment;
import ru.otus.springbatch.model.sql.CommentSQL;

import javax.sql.DataSource;

@Configuration
public class StepCommentsConfig {

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
    public JdbcCursorItemReader<CommentSQL> commentsReader() {
        return new JdbcCursorItemReaderBuilder<CommentSQL>()
                .name("commentsReader")
                .dataSource(dataSource)
                .sql("SELECT id, book_id, content FROM comments")
                .rowMapper((rs, rowNum) ->
                        new CommentSQL(
                                rs.getLong("id"),
                                rs.getLong("book_id"),
                                rs.getString("content")))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<CommentSQL, Comment> processorComments() {
        return processService::mapComment;
    }

    @StepScope
    @Bean
    public MongoItemWriter<Comment> commentsWriter() {
        return new MongoItemWriterBuilder<Comment>()
                .template(template)
                .collection("comment")
                .build();
    }

    @Bean
    public Step migrationCommentsStep(JdbcCursorItemReader<CommentSQL> commentsReader,
                                      MongoItemWriter<Comment> commentsWriter,
                                      ItemProcessor<CommentSQL, Comment> processorComments) {
        return stepBuilderFactory.get("migrationCommentsStep")
                .<CommentSQL, Comment>chunk(100)
                .reader(commentsReader)
                .processor(processorComments)
                .writer(commentsWriter)
                .build();
    }

}
