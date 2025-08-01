package com.tms.TaskManagement.exception.custom;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super("Task does not exist.");
    }
}
