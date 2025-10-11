package ru.webdl.otus.socialnetwork.infra.chat.dto;

import lombok.Getter;
import ru.webdl.otus.socialnetwork.core.chat.Chat;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ChatResponse {
    private final UUID chatId;
    private final UUID firstMemberId;
    private final UUID secondMemberId;
    private final UUID lastMessageId;
    private final UUID lastMessageSenderId;
    private final String lastMessageText;
    private final LocalDateTime lastMessageCreatedAt;

    public ChatResponse(Chat chat) {
        this.chatId = chat.getChatId();
        this.firstMemberId = chat.getFirstMemberId();
        this.secondMemberId = chat.getSecondMemberId();
        this.lastMessageId = chat.getLastMessageId();
        this.lastMessageSenderId = chat.getLastMessageSenderId();
        this.lastMessageText = chat.getLastMessageText();
        this.lastMessageCreatedAt = chat.getLastMessageCreatedAt();
    }
}
