package ru.otus.metamorphosis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.metamorphosis.domain.Egg;
import ru.otus.metamorphosis.integration.Growing;

@SpringBootApplication
public class MetamorphosisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MetamorphosisApplication.class, args);
        System.out.println("Вылетела бабочка: " + ctx.getBean(Growing.class).growingUp(new Egg(0)));
    }

}
