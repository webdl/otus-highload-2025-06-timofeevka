package ru.webdl.otus.socialnetwork.core.chat;

import ru.webdl.otus.socialnetwork.core.message.Message;

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

    boolean isLastMessage(Message message);

    boolean changeLastMessage(Message message);

    interface ChatBuilder {
        ChatBuilder chatId(UUID chatId);

        ChatBuilder firstMemberId(UUID firstMemberId);

        ChatBuilder secondMemberId(UUID secondMemberId);

        ChatBuilder lastMessageId(UUID lastMessageId);

        ChatBuilder lastMessageSenderId(UUID lastMessageSenderId);

        ChatBuilder lastMessageText(String lastMessageText);

        ChatBuilder lastMessageCreatedAt(LocalDateTime lastMessageCreatedAt);

        Chat build();
    }
}
