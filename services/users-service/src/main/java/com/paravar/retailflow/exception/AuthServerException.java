package com.paravar.retailflow.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthServerException extends  RuntimeException{
    int status;
    String message;
    public AuthServerException(String message) {
        super(message);
    }


    public static AuthServerException of(int status, String message) {
        return new AuthServerException(status, message);
    }
}
