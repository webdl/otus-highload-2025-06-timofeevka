package ru.webdl.otus.socialnetwork.core.user;

import java.util.UUID;

public interface UserRepository {
    User getBy(UUID userId);
}
