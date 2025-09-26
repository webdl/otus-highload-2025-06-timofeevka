package ru.webdl.otus.socialnetwork.core.author.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID userId) {
        super("Author with id " + userId + " not found");
    }
}
