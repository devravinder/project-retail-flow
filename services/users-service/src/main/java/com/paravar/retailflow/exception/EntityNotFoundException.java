package com.paravar.retailflow.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException of(Long id) {
        return new EntityNotFoundException("Entity with id " + id + " not found");
    }

    public static EntityNotFoundException of(String username) {
        return new EntityNotFoundException("Entity with username " + username + " not found");
    }
}
