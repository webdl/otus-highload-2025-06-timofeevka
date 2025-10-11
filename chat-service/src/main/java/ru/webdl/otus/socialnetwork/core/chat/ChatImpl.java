package ru.webdl.otus.socialnetwork.core.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.webdl.otus.socialnetwork.core.message.Message;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatImpl implements Chat {
    private UUID chatId;
    private final UUID firstMemberId;
    private final UUID secondMemberId;
    private UUID lastMessageId;
    private UUID lastMessageSenderId;
    private String lastMessageText;
    private LocalDateTime lastMessageCreatedAt;

    private UUID minMemberId;
    private UUID maxMemberId;

    void setLastMessage(@NonNull Message message) {
        this.lastMessageId = message.getMessageId();
        this.lastMessageSenderId = message.getSenderId();
        this.lastMessageText = message.getText().length() > 20 ? message.getText().substring(0, 20) : message.getText();
        this.lastMessageCreatedAt = message.getCreatedAt();
    }

    boolean isLastMessage(Message message) {
        return this.lastMessageId.equals(message.getMessageId());
    }
}
