package ru.webdl.otus.socialnetwork.core.user.cases.exceptions;

public class PasswordIncorrectException extends RuntimeException {
    public PasswordIncorrectException(String message) {
        super(message);
    }
}
