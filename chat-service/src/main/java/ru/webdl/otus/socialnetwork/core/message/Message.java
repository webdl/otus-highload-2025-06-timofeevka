package ru.webdl.otus.socialnetwork.core.message;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Message {
    UUID getMessageId();

    UUID getChatId();

    UUID getSenderId();

    String getText();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    boolean change(String text);

    interface MessageBuilder {
        MessageBuilder messageId(UUID messageId);

        MessageBuilder chatId(UUID chatId);

        MessageBuilder senderId(UUID senderId);

        MessageBuilder text(String text);

        MessageBuilder createdAt(LocalDateTime createdAt);

        MessageBuilder updatedAt(LocalDateTime updatedAt);

        Message build();
    }
}
