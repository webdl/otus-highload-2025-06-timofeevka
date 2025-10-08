package ru.webdl.otus.socialnetwork.core.member;

import ru.webdl.otus.socialnetwork.core.user.User;

public interface ResolveMembersUseCase {
    Member getOrCreate(User user);
}
