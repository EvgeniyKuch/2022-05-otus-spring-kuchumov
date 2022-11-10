package ru.otus.metamorphosis.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import ru.otus.metamorphosis.domain.Butterfly;
import ru.otus.metamorphosis.domain.Caterpillar;
import ru.otus.metamorphosis.domain.Chrysalis;
import ru.otus.metamorphosis.domain.Egg;

@Configuration
public class IntegrationConfig {

    @Bean
    public IntegrationFlow transformEgg() {
        return IntegrationFlows.from("transformEggToCaterpillar")
                .handle("eggService", "transform")
                .channel("growth")
                .get();
    }

    @Bean
    public IntegrationFlow transformCaterpillar() {
        return IntegrationFlows.from("transformCaterpillarToChrysalis")
                .handle("caterpillarService", "transform")
                .channel("growth")
                .get();
    }

    @Bean
    public IntegrationFlow transformChrysalis() {
        return IntegrationFlows.from("transformChrysalisToButterfly")
                .handle("chrysalisService", "transform")
                .channel("growth")
                .get();
    }

    @Bean
    DirectChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow growingUp() {
        return IntegrationFlows.from("growth")
                .<Object, Class<?>>route(Object::getClass, m -> m
                        .subFlowMapping(Egg.class, transformEgg())
                        .subFlowMapping(Caterpillar.class, transformCaterpillar())
                        .channelMapping(Chrysalis.class, "transformChrysalisToButterfly")
                        .channelMapping(Butterfly.class, "outputChannel"))
                .get();
    }

}
