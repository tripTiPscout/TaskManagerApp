package com.appfire.taskmanager.business.sevices;

import com.appfire.taskmanager.data.enums.Status;
import com.appfire.taskmanager.data.entities.Task;
import com.appfire.taskmanager.data.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task) {
        System.out.println("Task created!");
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(Integer id) {
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> getTaskByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    @Override
    public List<Task> getUnfinishedTasks() {
        return taskRepository.findByStatus(Status.IN_PROGRESS);
    }

    @Override
    public List<Task> getNewestTasks() {
        return taskRepository.findByStatus(Status.NEW);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public boolean deleteTaskById(Integer id) {
        taskRepository.deleteById(id);
        System.out.println("Task deleted!");
        return true;
    }

    @Override
    public boolean deleteTaskByTitle(String title) {
        Optional<Task> task = taskRepository.findByTitle(title);
        taskRepository.delete(task.orElseThrow());
        System.out.println("Task deleted!");
        return true;
    }

}
