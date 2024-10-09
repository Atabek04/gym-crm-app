package com.epam.gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class GymApp {
    public static void main(String[] args) {
        SpringApplication.run(GymApp.class, args);
    }
}
