package ru.webdl.otus.socialnetwork.core.user;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User findById(UUID userId);

    List<User> findUserFriends(UUID userId);
}
