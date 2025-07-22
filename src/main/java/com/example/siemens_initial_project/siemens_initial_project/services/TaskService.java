package com.example.siemens_initial_project.siemens_initial_project.services;

import java.time.LocalDate;
import java.util.List;

import com.example.siemens_initial_project.siemens_initial_project.dto.TaskDto;

public interface TaskService {

    TaskDto createTask(TaskDto taskDto);
    List<TaskDto> getAllTasks();
    TaskDto updateTask(Long id, TaskDto taskDto);
    void deleteTask(Long id);
    TaskDto markAsCompleted(Long id);
    List<TaskDto> filterTasks(String status, LocalDate dueDate);

}
