package com.tms.TaskManagement.exception.custom;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("An account already exists for email: " + email);
    }
}
