package com.tms.TaskManagement.exception.custom;

public class TeamAlreadyExistsException extends RuntimeException {
    public TeamAlreadyExistsException(String name) {
        super("Team already exists with name: " + name);
    }
}
