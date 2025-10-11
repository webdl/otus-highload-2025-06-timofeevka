package ru.webdl.otus.socialnetwork.core.message;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Message {
    UUID getMessageId();

    UUID getChatId();

    UUID getSenderId();

    String getText();

    LocalDateTime getCreatedAt();
}
