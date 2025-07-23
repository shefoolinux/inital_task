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

@Service
@AllArgsConstructor

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

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

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDto> taskDtos = taskMapper.toDtoList(tasks);
        return taskDtos;
    }

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

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task with id " + id + " not found");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto markAsCompleted(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));

        task.setStatus(TaskStatus.COMPLETED);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

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