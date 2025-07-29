package com.example.siemens_initial_project.siemens_initial_project.integration;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.siemens_initial_project.siemens_initial_project.dto.TaskDto;
import com.example.siemens_initial_project.siemens_initial_project.model.Task;
import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;
import com.example.siemens_initial_project.siemens_initial_project.repository.TaskRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void beforeSetUp() {
        baseUrl = "http://localhost:" + port + "/api/tasks";
    }

    @AfterEach
    public void afterSetUp() {
        taskRepository.deleteAll();
    }

    private TaskDto buildTaskDto(String title, TaskStatus status, LocalDate dueDate) {
        TaskDto dto = new TaskDto();
        dto.setTitle(title);
        dto.setDescription("Test description");
        dto.setStatus(status);
        dto.setDueDate(dueDate);
        return dto;
    }

    @Test
    public void createTask_ShouldCreateTaskSuccessfully() {
        TaskDto newTaskDto = buildTaskDto("New Task " + UUID.randomUUID(), TaskStatus.IN_PROGRESS, LocalDate.now().plusDays(2));

        TaskDto response = restTemplate.postForObject(baseUrl, newTaskDto, TaskDto.class);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(newTaskDto.getTitle(), response.getTitle());
        assertEquals(newTaskDto.getStatus(), response.getStatus());
        assertEquals(newTaskDto.getDescription(), response.getDescription());
        assertEquals(newTaskDto.getDueDate(), response.getDueDate());
    }

    @Test
    public void getAllTasks_ShouldReturnAllTasksSuccessfully() {
        TaskDto newTaskDto = buildTaskDto("New Task " + UUID.randomUUID(), TaskStatus.IN_PROGRESS, LocalDate.now().plusDays(2));

        ResponseEntity<TaskDto> response = restTemplate.postForEntity(baseUrl, newTaskDto, TaskDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ResponseEntity<List<TaskDto>> responseList = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TaskDto>>() {
        }
        );

        List<TaskDto> tasks = responseList.getBody();
        assertNotNull(tasks);
        assertTrue(tasks.size() >= 1);
        assertTrue(tasks.stream().anyMatch(t -> t.getTitle().equals(newTaskDto.getTitle())));
    }

    @Test
    public void deleteTask_ShouldDeleteSuccessfully() {
        TaskDto taskDto = buildTaskDto("Task to be deleted", TaskStatus.COMPLETED, LocalDate.now().plusDays(1));

        TaskDto createdTask = restTemplate.postForObject(baseUrl, taskDto, TaskDto.class);
        assertNotNull(createdTask);

        restTemplate.delete(baseUrl + "/" + createdTask.getId());

        assertTrue(taskRepository.findById(createdTask.getId()).isEmpty());
    }

    @Test
    public void updateTask_ShouldUpdateSuccessfully() {
        TaskDto taskDto = buildTaskDto("Task to be updated", TaskStatus.COMPLETED, LocalDate.now().plusDays(1));

        TaskDto createdTask = restTemplate.postForObject(baseUrl, taskDto, TaskDto.class);
        Long id = createdTask.getId();

        createdTask.setTitle("Updated Title");
        createdTask.setDescription("Updated Description");
        createdTask.setStatus(TaskStatus.CANCELLED);

        restTemplate.put(baseUrl + "/" + id, createdTask);

        Task updatedTask = taskRepository.findById(id).orElse(null);

        assertNotNull(updatedTask);
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals(TaskStatus.CANCELLED, updatedTask.getStatus());
    }

    @Test
    public void markAsCompleted_ShouldMakeTaskCompletedSuccessfully() {
        TaskDto taskDto = buildTaskDto("Task to be completed", TaskStatus.IN_PROGRESS, LocalDate.now().plusDays(1));

        TaskDto createdTask = restTemplate.postForObject(baseUrl, taskDto, TaskDto.class);
        Long id = createdTask.getId();

        restTemplate.put(baseUrl + "/" + id + "/complete", null);

        Task updatedTask = taskRepository.findById(id).orElse(null);

        assertNotNull(updatedTask);
        assertEquals(TaskStatus.COMPLETED, updatedTask.getStatus());
    }

    @Test
    public void filterTasks_ShouldReturnFilteredTasks() {
        TaskDto task1 = buildTaskDto("Task A", TaskStatus.PENDING, LocalDate.of(2025, 8, 1));
        restTemplate.postForObject(baseUrl, task1, TaskDto.class);

        TaskDto task2 = buildTaskDto("Task B", TaskStatus.COMPLETED, LocalDate.of(2025, 8, 1));
        restTemplate.postForObject(baseUrl, task2, TaskDto.class);

        String url = baseUrl + "/filter?status=COMPLETED&dueDate=2025-08-01";

        ResponseEntity<List<TaskDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TaskDto>>() {
        }
        );

        List<TaskDto> filteredTasks = response.getBody();

        assertNotNull(filteredTasks);
        assertEquals(1, filteredTasks.size());
        assertEquals("Task B", filteredTasks.get(0).getTitle());
        assertEquals(TaskStatus.COMPLETED, filteredTasks.get(0).getStatus());
    }
}
