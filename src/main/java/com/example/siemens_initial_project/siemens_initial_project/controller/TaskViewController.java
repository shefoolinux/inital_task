package com.example.siemens_initial_project.siemens_initial_project.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskViewController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @GetMapping
    public String viewAllTasks(Model model) {
        List<TaskDto> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTaskFromWeb(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        System.out.println(task.getDueDate() + "-----------------------------------------------");
        TaskDto taskDto = taskMapper.toDto(task);
        model.addAttribute("task", taskDto);
        return "tasks/update";
    }

    @PostMapping("/update")
    public String updateTask(@Valid TaskDto taskDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors found: " + bindingResult.getAllErrors());
            return "tasks/update";
        }

        taskService.updateTask(taskDto.getId(), taskDto);
        return "redirect:/tasks";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new TaskDto());
        return "tasks/create_task";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute TaskDto taskDto) {
        taskService.createTask(taskDto);
        return "tasks";
    }

    @GetMapping("/filter")
    public String filterTasks(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dueDate,
            @RequestParam(required = false) TaskStatus status,
            Model model) {

        List<TaskDto> filteredTasks = taskService.filterTasks(
                status != null ? status.name() : null,
                dueDate);
        model.addAttribute("tasks", filteredTasks);
        return "tasks/list";
    }

}
