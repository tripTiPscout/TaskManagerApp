package com.appfire.taskmanager.data.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Priority {

    HIGH("HIGH"),
    MEDIUM("MEDIUM"),
    LOW("LOW");

    private final String priority;

    Priority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return this.priority;
    }

    @JsonCreator
    public static Priority getPriorityName(String value) {

        for (Priority priority : Priority.values()) {
            if (priority.getPriority().equals(value)) {
                return priority;
            }
        }

        return null;
    }

}
