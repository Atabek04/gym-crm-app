package com.gymcrm.command;

import com.gymcrm.facade.GymFacade;
import com.gymcrm.model.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GymCrmCommands implements CommandMarker {
    private static final Logger logger = LoggerFactory.getLogger(GymCrmCommands.class);

    private final GymFacade facade;

    @Autowired
    public GymCrmCommands(GymFacade facade) {
        this.facade = facade;
    }

    @CliCommand(value = "create trainee", help = "Create a new Trainee")
    public String createTrainee(
            @CliOption(key = {"id"}, mandatory = true, help = "The ID of the trainee") int id,
            @CliOption(key = {"userId"}, mandatory = true, help = "The User ID associated with the trainee") int userId,
            @CliOption(key = {"dob"}, mandatory = true, help = "Date of Birth of the trainee") String dob,
            @CliOption(key = {"address"}, mandatory = true, help = "Address of the trainee") String address) {

        // Assuming your Trainee class has a constructor that matches these fields
        Trainee trainee = new Trainee(id, userId, LocalDate.parse(dob), address);
        facade.registerTrainee(trainee);

        return "Trainee registered successfully with ID: " + id;
    }

    @CliCommand(value = "update trainee", help = "Update an existing Trainee")
    public String updateTrainee(
            @CliOption(key = {"id"}, mandatory = true, help = "The ID of the trainee to update") int id,
            @CliOption(key = {"address"}, mandatory = true, help = "New address of the trainee") String newAddress) {

        // Logic to update trainee
        Trainee trainee = facade.getTraineeById(id);
        if (trainee == null) {
            return "Trainee not found with ID: " + id;
        }
        trainee.setAddress(newAddress);
        facade.updateTrainee(trainee);

        return "Trainee updated successfully.";
    }

    @CliCommand(value = "find-trainee", help = "Find a Trainee by ID")
    public String findTraineeById(
            @CliOption(key = {"id"}, mandatory = true, help = "The ID of the trainee to find") int id
    ) {
        System.out.println("Finding trainee with ID: " + id);
        Trainee trainee = facade.getTraineeById(id);
        if (trainee == null) {
            logger.error("Trainee not found with ID: {}", id);
            return "Trainee not found with ID: " + id;
        }
        logger.info("Trainee found: ID={}, Address={}", trainee.getId(), trainee.getAddress());
        return "Trainee found: ID=" + trainee.getId() + ", Address=" + trainee.getAddress();
    }
}