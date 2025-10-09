package ru.webdl.otus.socialnetwork.core.message;

import ru.webdl.otus.socialnetwork.core.chat.Chat;

import java.util.List;

public interface MessageRepository {
    List<Message> findByChat(Chat chat);

    Message save(Message message);
}
