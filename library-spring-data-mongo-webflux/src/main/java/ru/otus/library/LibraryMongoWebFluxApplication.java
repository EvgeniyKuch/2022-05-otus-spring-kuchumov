package ru.otus.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class LibraryMongoWebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryMongoWebFluxApplication.class, args);
    }

}
