package com.gymcrm.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public static void handleResourceNotFoundException(ResourceNotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage());
    }

    public static void handleGeneralException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage());
    }
}
