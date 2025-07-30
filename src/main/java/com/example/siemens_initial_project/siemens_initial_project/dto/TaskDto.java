package com.example.siemens_initial_project.siemens_initial_project.dto;

import java.time.LocalDate;

import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for Task.
 * <p>
 * This class is used to transfer task data between layers, such as from client
 * to server or between services.
 */
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    /**
     * The unique identifier of the task.
     */
    private Long id;

    /**
     * The title of the task.
     * <p>
     * This field is required and must not be blank.
     */
    @NotBlank(message = "Title is required")
    private String title;

    /**
     * A brief description of the task.
     * <p>
     * This field is required and must not be blank.
     */
    @NotBlank(message = "Description is required")
    private String description;

    /**
     * The due date for the task.
     * <p>
     * This date must be in the future.
     */
    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;

    /**
     * The current status of the task.
     */
    private TaskStatus status;

    //--------------- Setters And Getters-------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

}
