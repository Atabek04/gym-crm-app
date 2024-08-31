package com.gymcrm;

import com.gymcrm.facade.GymFacade;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.gymcrm")
public class GymCrmApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(GymCrmApplication.class);
        var gymCLI = context.getBean(GymCLI.class);
        gymCLI.start();
    }
}