package com.example.siemens_initial_project.siemens_initial_project.dto;

import java.time.LocalDate;

import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

     private Long id;

    private String title;

    private String description;

    private LocalDate dueDate;

    private TaskStatus status;
}