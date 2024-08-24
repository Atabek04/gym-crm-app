package com.gymcrm;

import com.gymcrm.config.StorageConfig;
import com.gymcrm.config.StorageInitializerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.gymcrm")
public class GymCrmApplication {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                GymCrmApplication.class, StorageConfig.class, StorageInitializerConfig.class)) {
            GymCLI gymCLI = context.getBean(GymCLI.class);
            gymCLI.start();
        }
    }
}