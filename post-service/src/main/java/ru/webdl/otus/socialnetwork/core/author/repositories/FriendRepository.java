package ru.webdl.otus.socialnetwork.core.author.repositories;

import ru.webdl.otus.socialnetwork.core.author.entities.Author;

import java.util.List;
import java.util.UUID;

public interface FriendRepository {

    List<Author> getFriends(UUID userId);

}
