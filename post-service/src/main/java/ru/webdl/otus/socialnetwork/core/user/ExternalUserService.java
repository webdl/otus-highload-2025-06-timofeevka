package ru.webdl.otus.socialnetwork.core.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExternalUserService {
    ExternalUser findById(UUID userId);

    List<ExternalUser> findUserFriends(UUID userId);
}
