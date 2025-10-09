package ru.webdl.otus.socialnetwork.infra.message.dto;

import lombok.Data;
import ru.webdl.otus.socialnetwork.core.message.Message;

import java.util.UUID;

@Data
public class MessageResponse {
    private final UUID messageId;
    private final UUID chatId;
    private final UUID senderId;
    private final String text;

    public MessageResponse(Message message) {
        this.messageId = message.getMessageId();
        this.chatId = message.getChatId();
        this.senderId = message.getSenderId();
        this.text = message.getText();
    }
}
