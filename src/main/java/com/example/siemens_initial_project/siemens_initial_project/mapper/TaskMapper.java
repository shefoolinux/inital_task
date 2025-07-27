package com.example.siemens_initial_project.siemens_initial_project.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.siemens_initial_project.siemens_initial_project.dto.TaskDto;
import com.example.siemens_initial_project.siemens_initial_project.model.Task;

/**
 * Mapper interface for converting between {@link Task} entities and
 * {@link TaskDto} objects.
 * <p>
 * This interface uses MapStruct to automatically generate the implementation at
 * compile time.
 * </p>
 * 
 * <p>
 * It supports single object mapping, list mapping, and partial updates.
 * </p>
 * 
 * @author AbdulShafi
 * @version 1.0
 */

@Mapper(componentModel = "spring")
public interface TaskMapper {

    /**
     * Converts a {@link Task} entity to a {@link TaskDto}.
     *
     * @param task the Task entity
     * @return the corresponding TaskDto
     */
    TaskDto toDto(Task task);

    /**
     * Converts a {@link TaskDto} to a {@link Task} entity.
     *
     * @param taskDto the Task DTO
     * @return the corresponding Task entity
     */
    Task toEntity(TaskDto taskDto);

    /**
     * Converts a list of {@link Task} entities to a list of {@link TaskDto}s.
     *
     * @param tasks the list of Task entities
     * @return the list of corresponding TaskDtos
     */
    List<TaskDto> toDtoList(List<Task> tasks);

    /**
     * Updates an existing {@link Task} entity with values from a {@link TaskDto},
     *
     * @param task    the target Task entity to update
     * @param taskDto the source Task DTO containing the new values
     * @return the updated task
     */
    Task update(@MappingTarget Task task, TaskDto taskDto);

}