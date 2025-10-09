package ru.webdl.otus.socialnetwork.core.chat;

import lombok.NonNull;
import ru.webdl.otus.socialnetwork.core.member.Member;

import java.util.Optional;
import java.util.UUID;

public interface ChatRepository {
    Optional<Chat> findById(@NonNull UUID id);

    Optional<Chat> findByMembers(@NonNull Member first, @NonNull Member second);

    Chat save(@NonNull Chat chat);
}
