package com.gymcrm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.Bootstrap;

@Configuration
@ComponentScan(basePackages = {"com.gymcrm", "com.gymcrm.command"})
public class GymCrmShellConfig {
    private static final Logger logger = LoggerFactory.getLogger(GymCrmShellConfig.class);

    public static void main(String[] args) {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.run();
        } catch (Exception e) {
            logger.error("An error occurred while running the shell", e);
        }
    }
}