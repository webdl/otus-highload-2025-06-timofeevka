package ru.webdl.otus.socialnetwork.core.chat;

import ru.webdl.otus.socialnetwork.core.message.Message;

public interface ChatEditUseCase {
    void updateLastMessage(Chat chat, Message message);

    void replaceLastMessageAfterDeletion(Chat chat, Message deletedMessage);
}
