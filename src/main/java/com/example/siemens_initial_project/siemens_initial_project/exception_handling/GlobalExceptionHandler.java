package com.example.siemens_initial_project.siemens_initial_project.exception_handling;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for all controllers in the application.
 * <p>
 * This class catches and handles exceptions thrown from controller methods
 * and returns a user-friendly error page.
 * </p>
 * 
 * @author Abdul Shafi
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles {@link IllegalArgumentException} exceptions and displays an error
     * page with a custom message.
     *
     * @param ex    the exception thrown
     * @param model the model used to pass data to the view
     * @return the error view name
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentHtml(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", "Invalid input: " + ex.getMessage());
        return "error";
    }

    /**
     * Handles all generic exceptions that are not explicitly handled elsewhere.
     *
     * @param ex    the exception thrown
     * @param model the model used to pass data to the view
     * @return the error view name
     */
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Something went wrong: " + ex.getMessage());
        return "error";
    }
}
