package ru.webdl.otus.socialnetwork.infra.message.dto;

import ru.webdl.otus.socialnetwork.core.message.Message;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageResponse(
        UUID messageId,
        UUID chatId,
        UUID senderId,
        String text,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static MessageResponse from(Message m) {
        return new MessageResponse(
                m.getMessageId(),
                m.getChatId(),
                m.getSenderId(),
                m.getText(),
                m.getCreatedAt(),
                m.getUpdatedAt()
        );
    }
}
