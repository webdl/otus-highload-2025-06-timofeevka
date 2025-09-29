package ru.webdl.otus.socialnetwork.core.user;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID userId, String message) {
        super("User with id " + userId + " not found. Error: " + message);
    }
}
