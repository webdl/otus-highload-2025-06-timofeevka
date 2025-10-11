package ru.webdl.otus.socialnetwork.core.member;

import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.UUID;

public interface ResolveMembersUseCase {
    Member getOrCreate(UUID userId);
}
