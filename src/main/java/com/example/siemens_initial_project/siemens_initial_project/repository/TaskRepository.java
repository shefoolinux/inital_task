package com.example.siemens_initial_project.siemens_initial_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.siemens_initial_project.siemens_initial_project.entity.Task;


public interface TaskRepository  extends JpaRepository<Task, Long> {


    @Query("SELECT t FROM Task t WHERE t.title = ?1")
    public Optional<Task> findByTitle(String title);
}