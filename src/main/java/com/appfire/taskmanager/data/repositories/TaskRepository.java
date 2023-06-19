package com.appfire.taskmanager.data.repositories;

import com.appfire.taskmanager.data.enums.Status;
import com.appfire.taskmanager.data.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByStatus(Status status);

    Optional<Task> findByTitle(String title);

}
