package ru.otus.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommand {

    private final JobOperator jobOperator;

    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator()
            throws JobParametersInvalidException, JobInstanceAlreadyExistsException,
            NoSuchJobException, NoSuchJobExecutionException {
        Long executionId = jobOperator.start("migrationLibrary", "");
        System.out.println(jobOperator.getSummary(executionId));
    }
}
