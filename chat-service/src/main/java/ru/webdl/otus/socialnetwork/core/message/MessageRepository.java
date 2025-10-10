package ru.webdl.otus.socialnetwork.core.message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {
    List<Message> findByChatId(UUID chatId);

    Message save(Message message);
}
