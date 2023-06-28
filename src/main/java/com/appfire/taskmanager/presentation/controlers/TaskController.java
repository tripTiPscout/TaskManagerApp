package com.appfire.taskmanager.presentation.controlers;

import com.appfire.taskmanager.business.sevices.TaskService;
import com.appfire.taskmanager.data.exports.TaskExporterImpl;
import com.appfire.taskmanager.data.entities.Task;
import com.appfire.taskmanager.data.enums.Priority;
import com.appfire.taskmanager.data.enums.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/task/{id}")
    public @ResponseBody Task getTaskWithId(@PathVariable("id") Integer id) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task with id " + id + " not found");
        } else {
            return task.get();
        }
    }

    @GetMapping("/task")
    public @ResponseBody Task getTaskWithTitle(@RequestParam(value = "title") String title) {
        Optional<Task> task = taskService.getTaskByTitle(title);
        if (task.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task with title '" + title + "' not found");
        } else {
            return task.get();
        }
    }

    @GetMapping("/unfinished")
    public List<Task> getUnfinishedTasks() {
        return taskService.getUnfinishedTasks();
    }

    @GetMapping("/newest")
    public List<Task> getNewestTasks() {
        return taskService.getNewestTasks();
    }

    @GetMapping("/all")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping("/task/{id}")
    public Task updateTask(@PathVariable("id") Integer id, @RequestBody Map<String, String> data) {
        Optional<Task> taskToUpdate = taskService.getTaskById(id);

        if (taskToUpdate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task with id '" + id + "' not found");
        } else {
            taskToUpdate.get()
                    .setTitle(data.getOrDefault("title", taskToUpdate.get().getTitle()));
            taskToUpdate.get()
                    .setDescription(data.getOrDefault("description", taskToUpdate.get().getDescription()));
            taskToUpdate.get()
                    .setDate(LocalDate.parse(data.getOrDefault("date", LocalDate.now().toString())));
            taskToUpdate.get()
                    .setPriority(Priority.valueOf(data.getOrDefault("priority", taskToUpdate.get().getPriority().getPriority())));
            taskToUpdate.get()
                    .setStatus(Status.valueOf(data.getOrDefault("status", taskToUpdate.get().getStatus().getStatus())));

            return taskService.updateTask(taskToUpdate.get());
        }
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Integer id) {
        boolean deleted = taskService.deleteTaskById(id);
        if (!deleted) {
            return new ResponseEntity<>("Task with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Deleted task with id " + id, HttpStatus.OK);
    }

    @DeleteMapping("/task")
    public ResponseEntity<String> deleteByTitle(@RequestParam(value = "title") String title) {
        boolean deleted = taskService.deleteTaskByTitle(title);
        if (!deleted) {
            return new ResponseEntity<>("Task with id " + title + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Deleted task with title " + title, HttpStatus.OK);
    }

    @GetMapping("/export/{format}")
    public ResponseEntity<String> exportTasks(@PathVariable("format") String format) {
        List<Task> tasks = taskService.getAllTasks();

        TaskExporterImpl exporter = new TaskExporterImpl(tasks, format);
        exporter.exportTasks();

        return new ResponseEntity<>("Tasks exported successfully", HttpStatus.OK);
    }

}
