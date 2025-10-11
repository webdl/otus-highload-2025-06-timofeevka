package ru.webdl.otus.socialnetwork.core.message;

import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.member.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetMessagesUseCase {
    Optional<Message> findById(UUID messageId);

    List<Message> findByChat(Member user, Chat chat);

    Optional<Message> findLastMessage(Chat chat);
}
