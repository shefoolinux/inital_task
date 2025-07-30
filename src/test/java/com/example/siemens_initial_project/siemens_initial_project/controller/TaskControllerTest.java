package com.example.siemens_initial_project.siemens_initial_project.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.siemens_initial_project.siemens_initial_project.dto.TaskDto;
import com.example.siemens_initial_project.siemens_initial_project.model.Task;
import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;
import com.example.siemens_initial_project.siemens_initial_project.services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @MockBean
    private TaskService taskService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private TaskDto taskDto;
    private Task taskEntity;
    private Long taskId;
    private LocalDate today;
    List<TaskDto> tasks = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        today = LocalDate.now();
        taskDto = new TaskDto(
                1L,
                "test task",
                "description",
                today.plusDays(1),
                TaskStatus.PENDING);

        taskEntity = new Task(
                taskDto.getId(),
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getDueDate(),
                taskDto.getStatus());

        taskId = taskEntity.getId();
        tasks.add(taskDto);
    }

    @Test
    public void createTask_ShouldCreateTaskSuccessfully() throws Exception {

        when(taskService.createTask(any(TaskDto.class))).thenReturn(taskDto);

        this.mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(taskDto.getTitle()))
                .andExpect(jsonPath("$.description").value(taskDto.getDescription()))
                .andExpect(jsonPath("$.dueDate").value(taskDto.getDueDate().toString()))
                .andExpect(jsonPath("$.status").value(taskDto.getStatus().name()));
    }

    @Test
    public void getAllTasks_ShouldReturnAllTasksSuccessfully() throws Exception {

        when(taskService.getAllTasks()).thenReturn(tasks);

        this.mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(tasks.size())));
    }

    @Test
    public void deleteTask_ShouldDeleteTaskByIDSuccessfully() throws Exception {
        doNothing().when(taskService).deleteTask(taskId);

        this.mockMvc.perform(delete("/api/tasks/{id}", taskId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateTask_ShouldUpdateTaskByIDSuccessfully() throws Exception {

        when(taskService.updateTask(anyLong(), any(TaskDto.class))).thenReturn(taskDto);

        this.mockMvc.perform(put("/api/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(taskDto.getTitle()))
                .andExpect(jsonPath("$.description").value(taskDto.getDescription()))
                .andExpect(jsonPath("$.dueDate").value(taskDto.getDueDate().toString()))
                .andExpect(jsonPath("$.status").value(taskDto.getStatus().name()));
    }

    @Test
    public void markAsCompleted_ShouldMakeTaskCompletedSuccessfully() throws Exception {

        TaskDto result = new TaskDto();
        result.setId(taskId);
        result.setTitle("Test Task");
        result.setDescription("Test Description");
        result.setDueDate(LocalDate.now());
        result.setStatus(TaskStatus.COMPLETED);

        when(taskService.markAsCompleted(anyLong())).thenReturn(result);

        this.mockMvc.perform(put("/api/tasks/{id}/complete", taskId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(result.getTitle()))
                .andExpect(jsonPath("$.description").value(result.getDescription()))
                .andExpect(jsonPath("$.dueDate").value(result.getDueDate().toString()))
                .andExpect(jsonPath("$.status").value(result.getStatus().name()));
    }

    @Test
    public void filterTasks_ShouldReturnFilteredTasks() throws Exception {

        TaskDto task1 = new TaskDto(1L, "Task 1", "Description 1", today, TaskStatus.PENDING);
        TaskDto task2 = new TaskDto(2L, "Task 2", "Description 2", today, TaskStatus.IN_PROGRESS);
        List<TaskDto> filteredTasks = new ArrayList<>();
        filteredTasks.add(task1);
        filteredTasks.add(task2);

        when(taskService.filterTasks(eq(TaskStatus.PENDING), any())).thenReturn(filteredTasks);

        mockMvc.perform(get("/api/tasks/filter")
                .param("status", "PENDING")
                .param("dueDate", today.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }

}
