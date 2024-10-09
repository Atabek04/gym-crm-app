package com.epam.gym.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DiskSpaceHealthIndicator implements HealthIndicator {

    private static final long THRESHOLD = 100 * 1024 * 1024;  // 100 MB threshold

    @Override
    public Health health() {
        File disk = new File("/");
        long freeSpace = disk.getFreeSpace();

        if (freeSpace >= THRESHOLD) {
            return Health.up().withDetail("Free Disk Space", freeSpace).build();
        } else {
            return Health.down().withDetail("Free Disk Space", freeSpace).build();
        }
    }
}