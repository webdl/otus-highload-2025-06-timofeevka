package ru.webdl.otus.socialnetwork.infra.user.entities;

import org.springframework.stereotype.Repository;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.author.FriendRepository;

import java.util.List;
import java.util.UUID;

@Repository
public class FriendRepositoryImpl implements FriendRepository {

    @Override
    public List<Author> getFriends(UUID userId) {
        return List.of();
    }

}
