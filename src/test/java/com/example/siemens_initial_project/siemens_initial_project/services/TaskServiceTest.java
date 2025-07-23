package com.example.siemens_initial_project.siemens_initial_project.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.siemens_initial_project.siemens_initial_project.dto.TaskDto;
import com.example.siemens_initial_project.siemens_initial_project.mapper.TaskMapper;
import com.example.siemens_initial_project.siemens_initial_project.model.Task;
import com.example.siemens_initial_project.siemens_initial_project.model.enums.TaskStatus;
import com.example.siemens_initial_project.siemens_initial_project.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    TaskServiceImpl taskService;
    @Mock
    TaskRepository taskRepository;
    @Mock
    TaskMapper taskMapper;

    private TaskDto taskDto;
    private Task taskEntity;
    private TaskDto updatedDto;
    private Task updatedTask;
    Long taskId;
    private LocalDate today;

    @BeforeEach
    public void setUp() {

        today = LocalDate.now();
        taskDto = new TaskDto(
                1L,
                "This is a test task",
                "description",
                today,
                TaskStatus.PENDING);

        taskEntity = new Task(
                taskDto.getId(),
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getDueDate(),
                taskDto.getStatus());

        taskId = taskEntity.getId();
    }

    @Test
    public void createTask_ShouldCreateTaskSuccessfully() {

        when(taskMapper.toEntity(taskDto)).thenReturn(taskEntity);
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);
        when(taskMapper.toDto(taskEntity)).thenReturn(taskDto);

        TaskDto createdTask = taskService.createTask(taskDto);

        assertNotNull(createdTask);
        assertEquals(taskDto, createdTask);
    }

    @Test
    public void getAllTasks_ShouldReturnAllTasksSuccessfully() {

        when(taskRepository.findAll()).thenReturn(List.of(taskEntity));
        when(taskMapper.toDtoList(List.of(taskEntity))).thenReturn(List.of(taskDto));

        List<TaskDto> tasks = taskService.getAllTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(taskDto.getTitle(), tasks.get(0).getTitle());
    }

    public void initializeUpdatedTask() {

        updatedDto = new TaskDto(
                taskId, "New Title",
                "Updated desc",
                today,
                TaskStatus.PENDING);

        updatedTask = new Task(
                updatedDto.getId(),
                updatedDto.getTitle(),
                updatedDto.getDescription(),
                updatedDto.getDueDate(),
                updatedDto.getStatus());
    }

    @Test
    public void updateTask_ShouldUpdateSuccessfully_WhenTitleIsChangedAndUnique() {

        initializeUpdatedTask();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(taskRepository.findByTitle(updatedDto.getTitle())).thenReturn(Optional.empty());
        doNothing().when(taskMapper).update(taskEntity, updatedDto);
        when(taskRepository.save(taskEntity)).thenReturn(updatedTask);
        when(taskMapper.toDto(updatedTask)).thenReturn(updatedDto);

        TaskDto result = taskService.updateTask(taskId, updatedDto);

        assertNotNull(result);
        assertEquals(updatedDto, result);

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository).findByTitle(updatedDto.getTitle());
        verify(taskMapper).update(taskEntity, updatedDto);
        verify(taskRepository).save(taskEntity);
        verify(taskMapper).toDto(updatedTask);
    }

    @Test
    public void deleteTask_ShouldDeleteTaskSuccessfully() {
        when(taskRepository.existsById(taskId)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).existsById(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void markAsCompleted_ShouldMarkTaskAsCompletedSuccessfully() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);
        when(taskMapper.toDto(taskEntity)).thenReturn(taskDto);

        TaskDto result = taskService.markAsCompleted(taskId); 

        assertNotNull(result);
        assertEquals(TaskStatus.COMPLETED, taskEntity.getStatus());

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(taskEntity);
        verify(taskMapper, times(1)).toDto(taskEntity);
    }

    @Test
    public void filterTasks_ShouldReturnFilteredTasks_WhenStatusIsPending() {

        when(taskRepository.findByStatus(TaskStatus.PENDING)).thenReturn(List.of(taskEntity));
        when(taskMapper.toDtoList(List.of(taskEntity))).thenReturn(List.of(taskDto));

        List<TaskDto> result = taskService.filterTasks(TaskStatus.PENDING, null);

        assertNotNull(result);
        assertEquals(result.get(0).getStatus(), TaskStatus.PENDING);
        verify(taskRepository, times(1)).findByStatus(TaskStatus.PENDING);
        verify(taskMapper, times(1)).toDtoList(List.of(taskEntity));
    }

    @Test
    public void filterTasks_ShouldReturnFilteredTasks_WhenDateIsToday() {

        when(taskRepository.findByDueDate(today)).thenReturn(List.of(taskEntity));
        when(taskMapper.toDtoList(List.of(taskEntity))).thenReturn(List.of(taskDto));

        List<TaskDto> result = taskService.filterTasks(null, taskEntity.getDueDate());

        assertNotNull(result);
        assertEquals(result.get(0).getDueDate(), taskEntity.getDueDate());

        verify(taskRepository, times(1)).findByDueDate(taskEntity.getDueDate());
        verify(taskMapper, times(1)).toDtoList(List.of(taskEntity));
    }

    @Test
    public void filterTasks_ShouldReturnFilteredTasks_WhenDateIsToday_And_StatusIsPending() {
        when(taskRepository.findByStatusAndDueDate(TaskStatus.PENDING, today)).thenReturn(List.of(taskEntity));
        when(taskMapper.toDtoList(List.of(taskEntity))).thenReturn(List.of(taskDto));

        List<TaskDto> result = taskService.filterTasks(TaskStatus.PENDING, taskEntity.getDueDate());

        assertNotNull(result);
        assertEquals(result.get(0).getDueDate(), taskEntity.getDueDate());
        assertEquals(result.get(0).getStatus(), TaskStatus.PENDING);
        verify(taskRepository, times(1)).findByStatusAndDueDate(TaskStatus.PENDING, taskEntity.getDueDate());
        verify(taskMapper, times(1)).toDtoList(List.of(taskEntity));
    }

}