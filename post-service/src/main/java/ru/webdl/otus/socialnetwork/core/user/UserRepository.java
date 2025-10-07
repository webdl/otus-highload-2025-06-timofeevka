package ru.webdl.otus.socialnetwork.core.user;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User getBy(UUID userId);

    List<User> getFriendsFor(User user);
}
