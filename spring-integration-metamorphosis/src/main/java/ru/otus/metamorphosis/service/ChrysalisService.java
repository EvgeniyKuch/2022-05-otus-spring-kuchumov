package ru.otus.metamorphosis.service;

import org.springframework.stereotype.Service;
import ru.otus.metamorphosis.domain.Butterfly;
import ru.otus.metamorphosis.domain.Chrysalis;

@Service
public class ChrysalisService {

    public Butterfly transform(Chrysalis chrysalis) {
        System.out.println("Пришёл кокон: " + chrysalis);
        var butterfly = new Butterfly(chrysalis.getAge() + 10);
        System.out.println("Создаём бабочку: " + butterfly);
        return butterfly;
    }
}
