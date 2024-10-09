package com.epam.gym.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ExternalServiceHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean serviceUp = checkExternalServiceHealth();

        if (serviceUp) {
            return Health.up().withDetail("External Service", "Available").build();
        } else {
            return Health.down().withDetail("External Service", "Not Available").build();
        }
    }

    private boolean checkExternalServiceHealth() {
        // Some logic for future external services
        return true;
    }
}