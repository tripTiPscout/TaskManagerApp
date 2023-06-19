package com.appfire.taskmanager;

import com.appfire.taskmanager.business.sevices.TaskServiceImpl;
import com.appfire.taskmanager.data.entities.Task;
import com.appfire.taskmanager.data.enums.Priority;
import com.appfire.taskmanager.data.enums.Status;
import com.appfire.taskmanager.presentation.controlers.TaskController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskServiceImpl taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        Task task = new Task();
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        Task createdTask = taskController.createTask(task);

        assertEquals(task, createdTask);
        verify(taskService, times(1)).createTask(task);
    }

    @Test
    void testGetTaskWithId_existingTask() {
        Task task = new Task();
        when(taskService.getTaskById(1)).thenReturn(Optional.of(task));

        Task retrievedTask = taskController.getTaskWithId(1);

        assertEquals(task, retrievedTask);
        verify(taskService, times(1)).getTaskById(1);
    }

    @Test
    void testGetTaskWithId_nonExistingTask() {
        when(taskService.getTaskById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> taskController.getTaskWithId(1));
        verify(taskService, times(1)).getTaskById(1);
    }

    @Test
    void testGetTaskWithTitle_existingTask() {
        Task task = new Task();
        when(taskService.getTaskByTitle("Test Task")).thenReturn(Optional.of(task));

        Task retrievedTask = taskController.getTaskWithTitle("Test Task");

        assertEquals(task, retrievedTask);
        verify(taskService, times(1)).getTaskByTitle("Test Task");
    }

    @Test
    void testGetTaskWithTitle_nonExistingTask() {
        when(taskService.getTaskByTitle("Test Task")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> taskController.getTaskWithTitle("Test Task"));
        verify(taskService, times(1)).getTaskByTitle("Test Task");
    }

    @Test
    void testGetUnfinishedTasks() {
        List<Task> unfinishedTasks = new ArrayList<>();
        when(taskService.getUnfinishedTasks()).thenReturn(unfinishedTasks);

        List<Task> retrievedTasks = taskController.getUnfinishedTasks();

        assertEquals(unfinishedTasks, retrievedTasks);
        verify(taskService, times(1)).getUnfinishedTasks();
    }

    @Test
    void testGetNewestTasks() {
        List<Task> newestTasks = new ArrayList<>();
        when(taskService.getNewestTasks()).thenReturn(newestTasks);

        List<Task> retrievedTasks = taskController.getNewestTasks();

        assertEquals(newestTasks, retrievedTasks);
        verify(taskService, times(1)).getNewestTasks();
    }

    @Test
    void testGetAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        when(taskService.getAllTasks()).thenReturn(allTasks);

        List<Task> retrievedTasks = taskController.getAllTasks();

        assertEquals(allTasks, retrievedTasks);
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void testUpdateTask_existingTask() {
        Map<String, String> data = new HashMap<>();
        data.put("title", "Updated Title");
        data.put("description", "Updated Description");
        data.put("date", "2023-06-13");
        data.put("priority", "HIGH");
        data.put("status", "IN_PROGRESS");

        Task existingTask = new Task();
        existingTask.setId(1);
        existingTask.setTitle("Old Title");
        existingTask.setDescription("Old Description");
        existingTask.setDate(LocalDate.now());
        existingTask.setPriority(Priority.MEDIUM);
        existingTask.setStatus(Status.IN_PROGRESS);

        when(taskService.getTaskById(1)).thenReturn(Optional.of(existingTask));
        when(taskService.updateTask(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task updatedTask = taskController.updateTask(1, data);

        assertEquals(existingTask, updatedTask);
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals("Updated Description", updatedTask.getDescription());
        assertEquals(LocalDate.parse("2023-06-13"), updatedTask.getDate());
        assertEquals(Priority.HIGH, updatedTask.getPriority());
        assertEquals(Status.IN_PROGRESS, updatedTask.getStatus());

        verify(taskService, times(1)).getTaskById(1);
        verify(taskService, times(1)).updateTask(existingTask);
    }

    @Test
    void testUpdateTask_nonExistingTask() {
        Map<String, String> data = new HashMap<>();
        data.put("title", "Updated Title");

        when(taskService.getTaskById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> taskController.updateTask(1, data));
        verify(taskService, times(1)).getTaskById(1);
    }

    @Test
    void testDeleteById_existingTask() {
        when(taskService.deleteTaskById(1)).thenReturn(true);

        ResponseEntity<String> response = taskController.deleteById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted task with id 1", response.getBody());

        verify(taskService, times(1)).deleteTaskById(1);
    }

    @Test
    void testDeleteById_nonExistingTask() {
        when(taskService.deleteTaskById(1)).thenReturn(false);

        ResponseEntity<String> response = taskController.deleteById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Task with id 1 not found", response.getBody());

        verify(taskService, times(1)).deleteTaskById(1);
    }

    @Test
    void testDeleteByTitle_existingTask() {
        when(taskService.deleteTaskByTitle("Test Task")).thenReturn(true);

        ResponseEntity<String> response = taskController.deleteByTitle("Test Task");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted task with title Test Task", response.getBody());

        verify(taskService, times(1)).deleteTaskByTitle("Test Task");
    }

    @Test
    void testDeleteByTitle_nonExistingTask() {
        when(taskService.deleteTaskByTitle("Test Task")).thenReturn(false);

        ResponseEntity<String> response = taskController.deleteByTitle("Test Task");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Task with id Test Task not found", response.getBody());

        verify(taskService, times(1)).deleteTaskByTitle("Test Task");
    }

}
