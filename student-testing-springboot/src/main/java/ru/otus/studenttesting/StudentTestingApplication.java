package ru.otus.studenttesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import ru.otus.studenttesting.service.TestingService;

@SpringBootApplication
@EnableCaching
public class StudentTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentTestingApplication.class, args).getBean(TestingService.class).testing();
    }
}
