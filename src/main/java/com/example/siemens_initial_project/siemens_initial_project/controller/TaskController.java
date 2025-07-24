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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Controller for handling all task-related operations

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Controller", description = "APIs for managing tasks")
public class TaskController {

  private final TaskService taskService;

  // ----------------------Get All Tasks----------------------

  @Operation(summary = "Get all tasks", description = "Returns a list of all tasks in the system.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved tasks")
  })
  @GetMapping
  public ResponseEntity<List<TaskDto>> getAllTasks() {
    List<TaskDto> tasks = taskService.getAllTasks();
    return ResponseEntity.ok(tasks);
  }

  // ----------------------Create A New Task ----------------------

  @Operation(summary = "Create a new task", description = "Creates a task based on the provided task data.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Task created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input")
  })
  @PostMapping
  public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
    TaskDto createdTask = taskService.createTask(taskDto);
    return ResponseEntity.ok(createdTask);
  }

  // ----------------------Update A Task ----------------------

  @Operation(summary = "Update a task", description = "Updates the task with the given ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Task updated successfully"),
      @ApiResponse(responseCode = "404", description = "Task not found")
  })
  @PutMapping("/{id}")
  public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
    TaskDto updatedTask = taskService.updateTask(id, taskDto);
    return ResponseEntity.ok(updatedTask);
  }

  // ----------------------Delete A Task ----------------------

  @Operation(summary = "Delete a task", description = "Deletes the task with the given ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Task not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
  }

  // ----------------------Mark Task As Completed ----------------------

  @Operation(summary = "Mark task as completed", description = "Marks the task with the given ID as completed.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Task marked as completed"),
      @ApiResponse(responseCode = "404", description = "Task not found")
  })
  @PutMapping("/{id}/complete")
  public ResponseEntity<TaskDto> markAsCompleted(@PathVariable Long id) {
    TaskDto updatedTask = taskService.markAsCompleted(id);
    return ResponseEntity.ok(updatedTask);
  }

  // ----------Filter Tasks By Status And Due Date ------------

  @Operation(summary = "Filter tasks", description = "Filters tasks based on status and/or due date.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Tasks filtered successfully")
  })
  @GetMapping("/filter")
  public ResponseEntity<List<TaskDto>> filterTasks(
      @RequestParam(required = false) TaskStatus status,
      @RequestParam(required = false) LocalDate dueDate) {
    List<TaskDto> filteredTasks = taskService.filterTasks(status, dueDate);
    return ResponseEntity.ok(filteredTasks);
  }
}
