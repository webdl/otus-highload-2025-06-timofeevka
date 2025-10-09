package ru.webdl.otus.socialnetwork.core.chat;

import java.util.UUID;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(UUID chatId) {
        super("Chat with id " + chatId + " not found");
    }
}
