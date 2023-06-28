package com.appfire.taskmanager.data.exports;

import com.appfire.taskmanager.data.entities.Task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;

public class TaskExporterImpl implements TaskExporter {

    private static final String EXPORT_FILE_PATH
            = "D:/IntelliJ_IDEA/Projects/TaskManager/src/main/resources/files/all_tasks";

    private File file;
    private final List<Task> tasks;
    private final String format;

    public TaskExporterImpl(List<Task> tasks, String format) {
        this.tasks = tasks;
        this.format = format;
    }

    @Override
    public void exportTasks() {
        switch (format.toLowerCase()) {
            case "txt" -> exportAsTxt();
            case "json" -> exportAsJson();
            default -> throw new IllegalArgumentException("Unsupported export format: " + format);
        }
    }

    private void exportAsTxt() {
        file = new File(EXPORT_FILE_PATH + ".txt");
        try {
            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write("Task ID: " + task.getId() + "\n");
                writer.write("Title: " + task.getTitle() + "\n");
                writer.write("Description: " + task.getDescription() + "\n");
                writer.write("Date: " + task.getDate() + "\n");
                writer.write("Priority: " + task.getPriority() + "\n");
                writer.write("Status: " + task.getStatus() + "\n");
                writer.write("\n");
            }
            writer.close();
            printStatus(file);
        } catch (IOException exception) {
            printStatus("TEXT", exception);
        }
    }

    private void exportAsJson() {
        file = new File(EXPORT_FILE_PATH + ".json");
        ObjectMapper objectMapper = getObjectMapper();
        try {
            objectMapper.writeValue(file, tasks);
            printStatus(file);
        } catch (IOException exception) {
            printStatus("JSON", exception);
        }
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        objectMapper.registerModule(new JavaTimeModule().addSerializer(LocalDate.class,
                new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer(dateFormatter)));
        objectMapper.registerModule(new JavaTimeModule().addDeserializer(LocalDate.class,
                new com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer(dateFormatter)));
        return objectMapper;
    }

    private void printStatus(File file) {
        System.out.printf("Tasks exported successfully to %s file.\n", file.getAbsolutePath());
    }

    private void printStatus(String fileType, IOException exception) {
        System.out.printf("Error during exporting tasks data to %s file: %s\n", fileType, exception.getMessage());
    }

}
