package com.appfire.taskmanager.data.entities;

import com.appfire.taskmanager.data.enums.Status;
import com.appfire.taskmanager.data.enums.Priority;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Priority priority;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    @JsonFormat(pattern="dd.MM.yyyy")
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Status status;

}
