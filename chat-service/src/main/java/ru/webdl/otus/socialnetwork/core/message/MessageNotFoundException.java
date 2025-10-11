package ru.webdl.otus.socialnetwork.core.message;

import java.util.UUID;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(UUID messageId) {
        super("Message with id " + messageId + " not found");
    }
}
