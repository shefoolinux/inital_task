package com.example.siemens_initial_project.siemens_initial_project.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.siemens_initial_project.siemens_initial_project.dto.TaskDto;
import com.example.siemens_initial_project.siemens_initial_project.mapper.TaskMapper;
import com.example.siemens_initial_project.siemens_initial_project.model.Task;
import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;
import com.example.siemens_initial_project.siemens_initial_project.repository.TaskRepository;

import lombok.AllArgsConstructor;

/**
 * Service implementation for managing tasks.
 * Provides business logic for creating, retrieving, updating, deleting, and
 * filtering tasks.
 */
@Service
@AllArgsConstructor

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    // ----------------------Create A New Task ----------------------

    /**
     * Creates a new task.
     * If the title already exists, it throws an IllegalStateException.
     * If status is not set, defaults to PENDING.
     *
     * @param taskDto the task data to create
     * @return the created TaskDto
     */

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);

        if (taskRepository.findByTitle(task.getTitle()).isPresent()) {
            throw new IllegalStateException("Task with title '" + task.getTitle() + "' already exists");
        }
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }

        Task savedTask = taskRepository.save(task);
        TaskDto savedTaskDto = taskMapper.toDto(savedTask);

        return savedTaskDto;
    }


    /**
     * Checks if a task with the given title already exists.
     *
     * @param title the title to check
     * @return true if a task with the title exists, false otherwise
     */
    @Override
    public boolean isTitleTaken(String title) {
        return taskRepository.existsByTitle(title);
    }

    // ----------------------Get All Tasks----------------------

    /**
     * Retrieves all tasks from the database.
     *
     * @return list of all TaskDto
     */

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDto> taskDtos = taskMapper.toDtoList(tasks);
        return taskDtos;
    }

// ----------------------Get Task By ID----------------------
    /**
     * Retrieves a task by its ID.
     * If the task is not found, throws IllegalArgumentException.
     *
     * @param id the ID of the task to retrieve
     * @return the TaskDto corresponding to the ID
     */
    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));
        return taskMapper.toDto(task);
    }


    // ----------------------Update A Task ----------------------

    /**
     * Updates a task by its ID.
     * If the task is not found, throws IllegalArgumentException.
     * If the new title already exists and is different, throws
     * IllegalStateException.
     *
     * @param id      the ID of the task to update
     * @param taskDto the new data for the task
     * @return the updated TaskDto
     */

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));

        String oldTitle = existingTask.getTitle();

        boolean isTitleChangedAndAlreadyExists = !oldTitle.equals(taskDto.getTitle()) &&
                taskRepository.findByTitle(taskDto.getTitle()).isPresent();

        if (isTitleChangedAndAlreadyExists) {
            throw new IllegalStateException("Task with title '" + taskDto.getTitle() + "' already exists");
        }

        taskMapper.update(existingTask, taskDto);

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toDto(updatedTask);
    }

    // ----------------------Delete A Task ----------------------

    /**
     * Deletes a task by its ID.
     * If the task is not found, throws IllegalArgumentException.
     *
     * @param id the ID of the task to delete
     */

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task with id " + id + " not found");
        }
        taskRepository.deleteById(id);
    }

    // ----------------------Mark Task As Completed ----------------------

    /**
     * Marks a task as COMPLETED by its ID.
     * If the task is not found, throws IllegalArgumentException.
     *
     * @param id the ID of the task to mark
     * @return the updated TaskDto with status COMPLETED
     */

    @Override
    public TaskDto markAsCompleted(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));

        task.setStatus(TaskStatus.COMPLETED);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    // ----------Filter Tasks By Status And Due Date ------------

    /**
     * Filters tasks based on status and/or due date.
     * Returns all tasks if no filter is provided.
     *
     * @param status  the status to filter by (nullable)
     * @param dueDate the due date to filter by (nullable)
     * @return list of TaskDto matching the filter
     */
    @Override
    public List<TaskDto> filterTasks(TaskStatus status, LocalDate dueDate) {
        List<Task> tasks;

        if (status != null && dueDate != null) {
            tasks = taskRepository.findByStatusAndDueDate(status, dueDate);
        } else if (status != null) {
            tasks = taskRepository.findByStatus(status);
        } else if (dueDate != null) {
            tasks = taskRepository.findByDueDate(dueDate);
        } else {
            tasks = taskRepository.findAll();
        }

        return taskMapper.toDtoList(tasks);
    }


    


}