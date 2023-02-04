package ru.otus.library.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class TooMuchRAMHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        long usedMBytes = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1_048_576;
        String message = String.format("Приложение потребляет %d MB памяти", usedMBytes);
        if (usedMBytes < 500) {
            return Health.up().withDetail("message", message).build();
        } else {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", message)
                    .build();

        }
    }
}
