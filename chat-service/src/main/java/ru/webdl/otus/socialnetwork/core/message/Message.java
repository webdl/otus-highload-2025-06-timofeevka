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

    interface Builder {
        Builder messageId(UUID messageId);

        Builder chatId(UUID chatId);

        Builder senderId(UUID senderId);

        Builder text(String text);

        Builder createdAt(LocalDateTime createdAt);

        Builder updatedAt(LocalDateTime updatedAt);

        Message build();
    }
}
