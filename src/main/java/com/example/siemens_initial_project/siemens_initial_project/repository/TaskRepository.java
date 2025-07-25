package com.example.siemens_initial_project.siemens_initial_project.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.siemens_initial_project.siemens_initial_project.model.Task;
import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;

/**
 * Repository interface for Task entities.
 * <p>
 * Provides methods to perform CRUD operations and custom queries on tasks.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find a task by its title.
     *
     * @param title the title of the task
     * @return an Optional containing the task if found, or empty otherwise
     */
    Optional<Task> findByTitle(String title);

    /**
     * Find all tasks with the given status.
     *
     * @param status the status to filter by
     * @return a list of tasks with the specified status
     */
    List<Task> findByStatus(TaskStatus status);

    /**
     * Find all tasks with the given due date.
     *
     * @param dueDate the due date to filter by
     * @return a list of tasks due on the specified date
     */
    List<Task> findByDueDate(LocalDate dueDate);

    /**
     * Find all tasks with the given status and due date.
     *
     * @param status the status to filter by
     * @param dueDate the due date to filter by
     * @return a list of tasks with the specified status and due date
     */
    List<Task> findByStatusAndDueDate(TaskStatus status, LocalDate dueDate);

    /**
     * Check if a task with the given title already exists.
     *
     * @param title the title to check
     * @return true if a task with the title exists, false otherwise
     */

    boolean existsByTitle(String title);

}
