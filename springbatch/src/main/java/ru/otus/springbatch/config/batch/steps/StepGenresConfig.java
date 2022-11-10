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
import ru.otus.springbatch.model.mongo.Genre;
import ru.otus.springbatch.model.sql.GenreSQL;

import javax.sql.DataSource;

@Configuration
public class StepGenresConfig {

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
    public JdbcCursorItemReader<GenreSQL> genresReader() {
        return new JdbcCursorItemReaderBuilder<GenreSQL>()
                .name("genresReader")
                .dataSource(dataSource)
                .sql("SELECT id, name FROM genres")
                .rowMapper((rs, rowNum) ->
                        new GenreSQL(
                                rs.getLong("id"),
                                rs.getString("name")))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<GenreSQL, Genre> processorGenres() {
        return processService::mapGenre;
    }

    @StepScope
    @Bean
    public MongoItemWriter<Genre> genresWriter() {
        return new MongoItemWriterBuilder<Genre>()
                .template(template)
                .collection("genre")
                .build();
    }

    @Bean
    public Step migrationGenresStep(JdbcCursorItemReader<GenreSQL> genresReader,
                                    MongoItemWriter<Genre> genresWriter,
                                    ItemProcessor<GenreSQL, Genre> processorGenres) {
        return stepBuilderFactory.get("migrationGenresStep")
                .<GenreSQL, Genre>chunk(100)
                .reader(genresReader)
                .processor(processorGenres)
                .writer(genresWriter)
                .build();
    }

}
