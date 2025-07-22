package com.example.siemens_initial_project.siemens_initial_project.mapper;

import org.mapstruct.Mapper;

import com.example.siemens_initial_project.siemens_initial_project.dto.TaskDto;
import com.example.siemens_initial_project.siemens_initial_project.entity.Task;

@Mapper
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toEntity(TaskDto taskDto);
}