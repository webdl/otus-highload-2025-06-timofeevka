package ru.webdl.otus.socialnetwork.core.user.cases.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
