package ru.otus.studenttesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StudentTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentTestingApplication.class, args);
    }
}
