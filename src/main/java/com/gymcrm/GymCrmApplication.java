package com.gymcrm;

import com.gymcrm.facade.GymFacade;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.gymcrm")
public class GymCrmApplication {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(GymCrmApplication.class);
        var facade = context.getBean(GymFacade.class);

//        System.out.println(facade.getTraineeById(1));
//        var trainee = facade.getTraineeById(1);
//        trainee.setId(101);
//        facade.updateTrainee(facade.getTraineeById(1));
        System.out.println(facade.getTraineeById(1));
    }
}