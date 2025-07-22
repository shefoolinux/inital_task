package com.example.siemens_initial_project.siemens_initial_project.dto;

import java.util.Date;

import com.example.siemens_initial_project.siemens_initial_project.utils.TaskStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskDto {

     private Long id;

    private String title;

    private String description;
    
    private Date dueDate;

    private TaskStatus status;
}