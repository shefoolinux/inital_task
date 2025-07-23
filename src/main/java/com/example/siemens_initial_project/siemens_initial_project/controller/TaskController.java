package com.example.siemens_initial_project.siemens_initial_project.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.siemens_initial_project.siemens_initial_project.dto.TaskDto;
import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;
import com.example.siemens_initial_project.siemens_initial_project.services.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @GetMapping
  public ResponseEntity<List<TaskDto>> getAllTasks() {
    List<TaskDto> tasks = taskService.getAllTasks();
    return ResponseEntity.ok(tasks);
  }

  @PostMapping
  public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
    TaskDto createdTask = taskService.createTask(taskDto);
    return ResponseEntity.ok(createdTask);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
    TaskDto updatedTask = taskService.updateTask(id, taskDto);
    return ResponseEntity.ok(updatedTask);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/complete")
  public ResponseEntity<TaskDto> markAsCompleted(@PathVariable Long id) {
    TaskDto updatedTask = taskService.markAsCompleted(id);
    return ResponseEntity.ok(updatedTask);
  }

  @GetMapping("/filter")
  public ResponseEntity<List<TaskDto>> filterTasks(@RequestParam(required = false) TaskStatus status,
      @RequestParam(required = false) LocalDate dueDate) {
    List<TaskDto> filteredTasks = taskService.filterTasks(status, dueDate);
    return ResponseEntity.ok(filteredTasks);

  }
}