package com.appfire.taskmanager.business.sevices;

import com.appfire.taskmanager.data.entities.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task createTask(Task task);

    Optional<Task> getTaskById(Integer id);

    Optional<Task> getTaskByTitle(String title);

    List<Task> getUnfinishedTasks();

    List<Task> getNewestTasks();

    List<Task> getAllTasks();

    Task updateTask(Task task);

    boolean deleteTaskById(Integer id);

    boolean deleteTaskByTitle(String title);

}
