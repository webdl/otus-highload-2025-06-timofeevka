package ru.webdl.otus.socialnetwork.core.user.entities;

import java.util.List;
import java.util.UUID;

public interface FriendRepository {

    List<User> getFriends(UUID userId);

}
