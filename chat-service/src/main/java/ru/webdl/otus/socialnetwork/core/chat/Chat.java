package ru.webdl.otus.socialnetwork.core.chat;

import ru.webdl.otus.socialnetwork.core.message.Message;

import java.util.UUID;

public interface Chat {
    UUID getChatId();

    UUID getFirstMemberId();

    UUID getSecondMemberId();

    UUID getLastMessageId();

    UUID getLastMessageSenderId();

    String getLastMessageText();

    UUID getMinMemberId();

    UUID getMaxMemberId();

    void setLastMessage(Message message);
}
