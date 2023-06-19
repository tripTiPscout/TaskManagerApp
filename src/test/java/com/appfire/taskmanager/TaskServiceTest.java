package com.appfire.taskmanager;

import com.appfire.taskmanager.business.sevices.TaskService;
import com.appfire.taskmanager.business.sevices.TaskServiceImpl;
import com.appfire.taskmanager.data.enums.Status;
import com.appfire.taskmanager.data.entities.Task;
import com.appfire.taskmanager.data.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private final TaskService taskService;

    public TaskServiceTest() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskServiceImpl(taskRepository);
    }

    @Test
    void getUnfinishedTasks_shouldReturnUnfinishedTasks() {
        Task unfinishedTask = new Task();
        unfinishedTask.setStatus(Status.NEW);

        when(taskRepository.findByStatus(Status.IN_PROGRESS)).thenReturn(Collections.singletonList(unfinishedTask));

        List<Task> tasks = taskService.getUnfinishedTasks();

        assertEquals(1, tasks.size());
        assertEquals(Status.NEW, tasks.get(0).getStatus());
    }

}
