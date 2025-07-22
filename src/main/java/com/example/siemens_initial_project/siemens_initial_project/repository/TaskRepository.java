package com.example.siemens_initial_project.siemens_initial_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.siemens_initial_project.siemens_initial_project.entity.Task;


public interface TaskRepository  extends JpaRepository<Task, Long> {
}