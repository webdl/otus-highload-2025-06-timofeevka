package ru.webdl.otus.socialnetwork.infra.chat.dto;

import ru.webdl.otus.socialnetwork.core.chat.Chat;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChatResponse(
        UUID chatId,
        UUID firstMemberId,
        UUID secondMemberId,
        UUID lastMessageId,
        UUID lastMessageSenderId,
        String lastMessageText,
        LocalDateTime lastMessageCreatedAt
) {
    public static ChatResponse from(Chat c) {
        return new ChatResponse(
                c.getChatId(),
                c.getFirstMemberId(),
                c.getSecondMemberId(),
                c.getLastMessageId(),
                c.getLastMessageSenderId(),
                c.getLastMessageText(),
                c.getLastMessageCreatedAt()
        );
    }
}
