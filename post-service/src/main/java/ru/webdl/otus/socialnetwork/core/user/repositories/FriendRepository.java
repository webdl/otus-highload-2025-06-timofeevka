package ru.webdl.otus.socialnetwork.core.user.repositories;

import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.util.List;
import java.util.UUID;

public interface FriendRepository {

    List<User> getFriends(UUID userId);

}
