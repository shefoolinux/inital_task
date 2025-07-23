package com.example.siemens_initial_project.siemens_initial_project.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.siemens_initial_project.siemens_initial_project.model.Task;
import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {

    public Optional<Task> findByTitle(String title);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByDueDate(LocalDate dueDate);

    List<Task> findByStatusAndDueDate(TaskStatus status, LocalDate dueDate);

}