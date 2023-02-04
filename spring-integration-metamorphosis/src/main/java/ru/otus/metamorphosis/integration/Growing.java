package ru.otus.metamorphosis.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.metamorphosis.domain.Butterfly;
import ru.otus.metamorphosis.domain.Egg;

@MessagingGateway
public interface Growing {

    @Gateway(requestChannel = "growth", replyChannel = "outputChannel")
    Butterfly growingUp(Egg egg);
}
