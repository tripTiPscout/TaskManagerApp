package com.appfire.taskmanager.data.exports;

import com.appfire.taskmanager.data.entities.Task;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TaskExporterImpl implements TaskExporter {
    private static final String EXPORT_FILE_PATH = "D:/IntelliJ_IDEA/Projects/TaskManager/src/main/resources/files/all_tasks";

    private final List<Task> tasks;
    private final String format;

    public TaskExporterImpl(List<Task> tasks, String format) {
        this.tasks = tasks;
        this.format = format;
    }

    @Override
    public void exportTasks() {
        try {
            switch (format.toLowerCase()) {
                case "txt" -> exportAsTxt();
                case "json" -> exportAsJson();
                default -> throw new IllegalArgumentException("Unsupported export format: " + format);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportAsTxt() throws IOException {
        File file = new File(EXPORT_FILE_PATH + ".txt");
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
        System.out.println("Tasks exported to " + file.getAbsolutePath());
    }

    private void exportAsJson() throws IOException {
        File file = new File(EXPORT_FILE_PATH + ".json");
        ObjectMapper objectMapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(df);
        objectMapper.writeValue(file, tasks);

        System.out.println("Tasks exported to " + file.getAbsolutePath());
    }

}
