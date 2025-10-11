package ru.webdl.otus.socialnetwork.core.chat;

import lombok.NonNull;
import ru.webdl.otus.socialnetwork.core.member.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetChatsUseCase {
    Optional<Chat> findById(@NonNull UUID id);

    List<Chat> findByMember(@NonNull Member member);
}
