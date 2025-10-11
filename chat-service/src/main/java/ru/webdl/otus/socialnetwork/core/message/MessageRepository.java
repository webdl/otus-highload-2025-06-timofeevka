package ru.webdl.otus.socialnetwork.core.message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {
    Optional<Message> findByMessageId(UUID id);

    List<Message> findByChatId(UUID chatId);

    Optional<Message> findLastMessage(UUID chatId);

    Message save(Message message);

    void delete(Message message);
}
