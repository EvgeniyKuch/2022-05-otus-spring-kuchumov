package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.TestingService;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class Main {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(Main.class).getBean(TestingService.class).testing();
    }
}
