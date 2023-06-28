package com.appfire.taskmanager.data.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {

    NEW("NEW"),
    IN_PROGRESS("IN PROGRESS"),
    DONE("DONE");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    @JsonCreator
    public static Status getStatusName(String value) {

        for (Status status : Status.values()) {
            if (status.getStatus().equals(value)) {
                return status;
            }
        }

        return null;
    }

}
