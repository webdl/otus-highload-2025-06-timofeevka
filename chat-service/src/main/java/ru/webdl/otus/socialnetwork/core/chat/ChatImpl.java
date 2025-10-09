package ru.webdl.otus.socialnetwork.core.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.webdl.otus.socialnetwork.core.message.Message;

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

    private UUID minMemberId;
    private UUID maxMemberId;

    @Override
    public void setLastMessage(@NonNull Message message) {
        this.lastMessageId = message.getMessageId();
        this.lastMessageSenderId = message.getSenderId();
        this.lastMessageText = message.getText().substring(0, 20);
    }
}
