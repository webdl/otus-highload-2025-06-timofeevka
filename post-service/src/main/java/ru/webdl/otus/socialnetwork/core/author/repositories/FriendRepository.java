package ru.webdl.otus.socialnetwork.core.author.repositories;

import ru.webdl.otus.socialnetwork.core.author.entities.User;

import java.util.List;
import java.util.UUID;

public interface FriendRepository {

    List<User> getFriends(UUID userId);

}
