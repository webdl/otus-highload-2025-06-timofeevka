package ru.webdl.otus.socialnetwork.core.chat;

import lombok.NonNull;
import ru.webdl.otus.socialnetwork.core.member.Member;

public interface ChatCreationUseCase {
    Chat create(@NonNull Member first, @NonNull Member second);
}
