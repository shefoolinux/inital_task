package com.example.siemens_initial_project.siemens_initial_project.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.siemens_initial_project.siemens_initial_project.dto.TaskDto;
import com.example.siemens_initial_project.siemens_initial_project.model.Task;

@Mapper(componentModel = "Spring")
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toEntity(TaskDto taskDto);

    List<TaskDto> toDtoList(List<Task> tasks);

    @Mapping(target = "task.id", ignore = true)
    void update(@MappingTarget Task task, TaskDto taskDto);

}
