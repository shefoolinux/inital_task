package com.example.siemens_initial_project.siemens_initial_project.services;

import java.time.LocalDate;
import java.util.List;

import com.example.siemens_initial_project.siemens_initial_project.dto.TaskDto;
import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;

/**
 * Service interface for managing tasks.
 * <p>
 * Defines the operations for creating, retrieving, updating,
 * deleting, completing, and filtering tasks.
 */
public interface TaskService {

    /**
     * Creates a new task.
     *
     * @param taskDto the task data to be created
     * @return the created TaskDto
     */
    TaskDto createTask(TaskDto taskDto);

    /**
     * Retrieves all tasks.
     *
     * @return a list of all TaskDto
     */
    List<TaskDto> getAllTasks();

    /**
     * Updates an existing task.
     *
     * @param id the ID of the task to update
     * @param taskDto the new task data
     * @return the updated TaskDto
     */
    TaskDto updateTask(Long id, TaskDto taskDto);

    /**
     * Deletes a task by ID.
     *
     * @param id the ID of the task to delete
     */
    void deleteTask(Long id);

    /**
     * Marks a task as completed.
     *
     * @param id the ID of the task to mark as completed
     * @return the updated TaskDto with status COMPLETED
     */
    TaskDto markAsCompleted(Long id);

    /**
     * Filters tasks by status and/or due date.
     *
     * @param status the status to filter by (can be null)
     * @param dueDate the due date to filter by (can be null)
     * @return a list of filtered TaskDto
     */
    List<TaskDto> filterTasks(TaskStatus status, LocalDate dueDate);


    /**
     * Checks if a task with the given title already exists.
     *
     * @param title the title to check
     * @return true if a task with the title exists, false otherwise
     */
    boolean isTitleTaken(String title);

    TaskDto getTaskById(Long id);
}
