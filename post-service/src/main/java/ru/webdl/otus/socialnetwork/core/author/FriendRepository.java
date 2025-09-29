package ru.webdl.otus.socialnetwork.core.author;

import java.util.List;
import java.util.UUID;

public interface FriendRepository {
    List<Author> getFriends(UUID userId);
}
