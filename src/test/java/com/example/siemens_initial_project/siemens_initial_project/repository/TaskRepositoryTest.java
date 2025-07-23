package com.example.siemens_initial_project.siemens_initial_project.repository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.siemens_initial_project.siemens_initial_project.model.Task;
import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;
    Task task;

    @BeforeEach
    public void setUp() {
        task = new Task(null, "Test Task", "This is a test task", LocalDate.now(), TaskStatus.IN_PROGRESS);
    }

    @Test
    public void testSaveAndFindByTitle() {
        // when
        taskRepository.save(task);
        Task foundTask = taskRepository.findByTitle("Test Task").orElse(null);
        // then
        assertNotNull(foundTask);
        assertEquals("Test Task", foundTask.getTitle());
    }

    @Test
    public void testSaveAndFindByStatus() {
        // when
        taskRepository.save(task);
        List<Task> foundTasks = taskRepository.findByStatus(TaskStatus.IN_PROGRESS);
        // then
        assertNotNull(foundTasks);
        assertEquals(TaskStatus.IN_PROGRESS, foundTasks.get(0).getStatus());
    }

    @Test
    public void testSaveAndFindByDueDate() {
        // when
        taskRepository.save(task);
        List<Task> foundTasks = taskRepository.findByDueDate(task.getDueDate());
        // then
        assertNotNull(foundTasks);
        assertEquals(task.getDueDate(), foundTasks.get(0).getDueDate());
    }

    @Test
    public void testSaveAndFindByStatusAndDueDate() {
        // when
        taskRepository.save(task);
        List<Task> foundTasks = taskRepository.findByStatusAndDueDate(TaskStatus.IN_PROGRESS, task.getDueDate());

        // then
        assertNotNull(foundTasks);
        assertEquals(TaskStatus.IN_PROGRESS, foundTasks.get(0).getStatus());
        assertEquals(task.getDueDate(), foundTasks.get(0).getDueDate());
    }

}