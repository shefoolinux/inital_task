package com.example.siemens_initial_project.siemens_initial_project.exception_handling;

/**
 * Custom exception for not found resources (e.g. task not found).
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}