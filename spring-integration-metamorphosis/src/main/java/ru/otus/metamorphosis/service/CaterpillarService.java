package ru.otus.metamorphosis.service;

import org.springframework.stereotype.Service;
import ru.otus.metamorphosis.domain.Caterpillar;
import ru.otus.metamorphosis.domain.Chrysalis;

@Service
public class CaterpillarService {

    public Chrysalis transform(Caterpillar caterpillar) {
        System.out.println("Пришла личинка: " + caterpillar);
        var chrysalis = new Chrysalis(caterpillar.getAge() + 10);
        System.out.println("Создаём кокон: " + chrysalis);
        return chrysalis;
    }
}
