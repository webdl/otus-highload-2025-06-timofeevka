package ru.webdl.otus.socialnetwork.core.chat;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Chat {
    UUID getChatId();

    UUID getFirstMemberId();

    UUID getSecondMemberId();

    UUID getLastMessageId();

    UUID getLastMessageSenderId();

    String getLastMessageText();

    LocalDateTime getLastMessageCreatedAt();

    UUID getMinMemberId();

    UUID getMaxMemberId();
}
