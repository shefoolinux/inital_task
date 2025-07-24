package com.example.siemens_initial_project.siemens_initial_project.model;

import java.time.LocalDate;

import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Represents a Task entity stored in the database.
 * Each task has a unique title, description, due date, and status.
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "title" }, name = "unique_title_constraint"))
public class Task {

    /**
     * The unique identifier of the task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the task. Must be unique and not null.
     */
    @Column(nullable = false)
    private String title;

    /**
     * A detailed description of the task.
     */
    @Column(nullable = false)
    private String description;

    /**
     * The due date by which the task should be completed.
     */
    @Column(nullable = false)
    private LocalDate dueDate;

    /**
     * The current status of the task.
     */
    @Column(nullable = false)
    private TaskStatus status;

}
