package ru.otus.springbatch.config.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import ru.otus.springbatch.ProcessService;

@Configuration
public class JobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private ProcessService processService;

    @Bean
    public Job migrationLibrary(Step migrationAuthorsStep, Step migrationGenresStep,
                                Step migrationBooksStep, Step migrationCommentsStep) {
        return jobBuilderFactory.get("migrationLibrary")
                .incrementer(new RunIdIncrementer())
                .flow(migrationAuthorsStep)
                .next(migrationGenresStep)
                .next(migrationBooksStep)
                .next(migrationCommentsStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        processService.clearAllCaches();
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        processService.clearAllCaches();
                    }
                })
                .build();
    }
}
