package com.example.siemens_initial_project.siemens_initial_project.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import static org.springframework.data.domain.Sort.Direction.ASC;
import org.springframework.stereotype.Service;

import com.example.siemens_initial_project.siemens_initial_project.dto.TaskDto;
import com.example.siemens_initial_project.siemens_initial_project.exception_handling.ResourceNotFoundException;
import com.example.siemens_initial_project.siemens_initial_project.mapper.TaskMapper;
import com.example.siemens_initial_project.siemens_initial_project.model.Task;
import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;
import com.example.siemens_initial_project.siemens_initial_project.repository.TaskRepository;

import lombok.AllArgsConstructor;

/**
 * Service implementation for managing tasks. Provides business logic for
 * creating, retrieving, updating, deleting, and filtering tasks.
 */
@Service
@AllArgsConstructor

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    /**
     * Finds a task by ID or throws if not found.
     *
     * @param id the ID of the task to retrieve
     * @return the found {@link Task}
     * @throws ResourceNotFoundException if the task with the given ID is not
     * found
     */
    private Task getTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
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

    // ----------------------Create A New Task ----------------------
    /**
     * Creates a new task from the given {@link TaskDto}.
     * <p>
     * Ensures the task title is unique and sets the default status to
     * {@code PENDING} if not provided.
     *
     * @param taskDto the task data to create
     * @return the created task as a {@link TaskDto}
     * @throws IllegalStateException if a task with the same title already
     * exists
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
        return taskMapper.toDto(savedTask);
    }

    // --------------------------------Get All Tasks-------------------------------------

    
    /**
     * Retrieves all tasks sorted by ID in ascending order.
     *
     * @return list of all tasks as {@link TaskDto}
     */
    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll(Sort.by(ASC, "id"));
        return taskMapper.toDtoList(tasks);
    }


    // --------------------------------Get Task By ID-------------------------------------


    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task
     * @return the task as {@link TaskDto}
     * @throws ResourceNotFoundException if the task is not found
     */
    @Override
    public TaskDto getTaskById(Long id) {
        Task task = getTaskOrThrow(id);
        return taskMapper.toDto(task);
    }



    // --------------------------------Update A Task-------------------------------------

    /**
     * Updates an existing task with new data.
     * <p>
     * If the title has changed, ensures the new title is not already used by
     * another task.
     *
     * @param id the ID of the task to update
     * @param taskDto the new task data
     * @return the updated task as {@link TaskDto}
     * @throws ResourceNotFoundException if the task with the given ID does not
     * exist
     * @throws IllegalStateException if the new title is already in use by
     * another task
     */
    
    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task existingTask = getTaskOrThrow(id);

        String oldTitle = existingTask.getTitle();

        boolean isTitleChangedAndAlreadyExists = !oldTitle.equals(taskDto.getTitle())
                && taskRepository.findByTitle(taskDto.getTitle()).isPresent();

        if (isTitleChangedAndAlreadyExists) {
            throw new IllegalStateException("Task with title '" + taskDto.getTitle() + "' already exists");
        }

        taskDto.setId(existingTask.getId());
        existingTask = taskMapper.update(existingTask, taskDto);

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toDto(updatedTask);
    }

    
    // --------------------------------Delete A Task-------------------------------------

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     * @throws ResourceNotFoundException if the task with the given ID does not
     * exist
     */
    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task with id " + id + " not found");
        }
        taskRepository.deleteById(id);
    }


    // --------------------------------Mark Task As Completed-------------------------------------

    /**
     * Marks a task as completed by updating its status to {@code COMPLETED}.
     *
     * @param id the ID of the task to update
     * @return the updated task as {@link TaskDto}
     * @throws ResourceNotFoundException if the task with the given ID is not
     * found
     */
    @Override
    public TaskDto markAsCompleted(Long id) {

        Task task = getTaskOrThrow(id);

        task.setStatus(TaskStatus.COMPLETED);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    // ----------------------------Filter Tasks By Status And Due Date --------------------------------

    /**
     * Filters tasks by status, due date, or both.
     * <p>
     * If both parameters are null, returns all tasks sorted by ID.
     *
     * @param status the status to filter by (nullable)
     * @param dueDate the due date to filter by (nullable)
     * @return list of matching tasks as {@link TaskDto}
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
            tasks = taskRepository.findAll(Sort.by(ASC, "id"));
        }

        return taskMapper.toDtoList(tasks);
    }

}
