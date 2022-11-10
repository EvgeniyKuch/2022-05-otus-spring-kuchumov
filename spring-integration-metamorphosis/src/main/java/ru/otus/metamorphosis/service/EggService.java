package ru.otus.metamorphosis.service;

import org.springframework.stereotype.Service;
import ru.otus.metamorphosis.domain.Caterpillar;
import ru.otus.metamorphosis.domain.Egg;

@Service
public class EggService {

    public Caterpillar transform(Egg egg) {
        System.out.println("Пришло яйцо: " + egg);
        var caterpillar = new Caterpillar(egg.getAge() + 10);
        System.out.println("Создаём личинку: " + caterpillar);
        return caterpillar;
    }
}
