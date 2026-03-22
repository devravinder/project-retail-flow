package com.paravar.retailflow.exception;

public class EmailAlreadyExists extends RuntimeException{
    public EmailAlreadyExists(String message) {
        super(message);
    }

    public static EmailAlreadyExists of(String email) {
        return new EmailAlreadyExists(email+" email already exists");
    }
}
