package com.example.siemens_initial_project.siemens_initial_project.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.siemens_initial_project.siemens_initial_project.dto.TaskDto;
import com.example.siemens_initial_project.siemens_initial_project.mapper.TaskMapper;
import com.example.siemens_initial_project.siemens_initial_project.model.Task;
import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;
import com.example.siemens_initial_project.siemens_initial_project.repository.TaskRepository;
import com.example.siemens_initial_project.siemens_initial_project.services.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * This controller is used to handle all task-related web pages.
 * It allows users to view, create, update, delete, and filter tasks
 * using Thymeleaf templates.
 * 
 * @author AbdulShafi
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskViewController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    // ----------------------Get All Tasks----------------------

    /**
     * Show all tasks on the main page.
     * 
     * @param model used to send data to the view
     * @return the page that shows the list of tasks
     */
    @GetMapping
    public String viewAllTasks(Model model) {
        List<TaskDto> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    // ----------------------Delete A Task ----------------------

    /**
     * Delete a task by its ID.
     * 
     * @param id ID of the task to be deleted
     * @return redirect to the list after deletion
     */
    @GetMapping("/delete/{id}")
    public String deleteTaskFromWeb(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    // ----------------------Update A Task ----------------------

    /**
     * Show the update form for a specific task.
     * 
     * @param id    the ID of the task to update
     * @param model to send task data to the update page
     * @return the update page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        TaskDto taskDto = taskMapper.toDto(task);
        model.addAttribute("task", taskDto);
        return "tasks/update";
    }

    /**
     * Save the updated task after editing.
     * 
     * @param taskDto       the task data from the form
     * @param bindingResult used to check for form errors
     * @return the updated list or the same page if there are errors
     */
   @PostMapping("/update")
public String updateTask(@Valid TaskDto taskDto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
        model.addAttribute("task", taskDto);
        return "tasks/update";
    }

    TaskDto existingTask = taskService.getTaskById(taskDto.getId());

    boolean isDuplicate = taskService.isTitleTaken(taskDto.getTitle());

    if (isDuplicate && !taskDto.getTitle().equals(existingTask.getTitle())) {
        model.addAttribute("task", taskDto);
        model.addAttribute("error", "A task with this title already exists.");
        return "tasks/update";
    }

    taskService.updateTask(taskDto.getId(), taskDto);
    return "redirect:/tasks";
}

    // ----------------------Create A New Task ----------------------

    /**
     * Show the form to create a new task.
     * 
     * @param model to send a new empty task to the form
     * @return the create task page
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new TaskDto());
        return "tasks/create_task";
    }

    /**
     * Save the new task after filling the form.
     * 
     * @param taskDto       the new task data
     * @param bindingResult to check for form validation
     * @return redirect to list or show same form if there are errors
     */
    @PostMapping("/create")
    public String createTask(@Valid TaskDto taskDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("task", taskDto);
            return "tasks/create_task";
        }

        boolean isDuplicate = taskService.isTitleTaken(taskDto.getTitle());
        if (isDuplicate) {
            model.addAttribute("task", taskDto);
            model.addAttribute("error", "A task with this title already exists.");
            return "tasks/create_task";
        }

        taskService.createTask(taskDto);
        return "redirect:/tasks";
    }

    // ----------Filter Tasks By Status And Due Date ------------

    /**
     * Filter tasks by due date and/or status.
     * 
     * @param dueDate the date to filter by (can be null)
     * @param status  the status to filter by (can be null)
     * @param model   to send filtered results to the view
     * @return the tasks list with filtered results
     */
    @GetMapping("/filter")
    public String filterTasks(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dueDate,
            @RequestParam(required = false) TaskStatus status,
            Model model) {

        List<TaskDto> filteredTasks = taskService.filterTasks(status, dueDate);
        model.addAttribute("tasks", filteredTasks);
        return "tasks/list";
    }
}
