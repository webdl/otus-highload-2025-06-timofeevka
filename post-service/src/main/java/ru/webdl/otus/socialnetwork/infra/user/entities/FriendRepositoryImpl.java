package ru.webdl.otus.socialnetwork.infra.user.entities;

import org.springframework.stereotype.Repository;
import ru.webdl.otus.socialnetwork.core.author.repositories.FriendRepository;
import ru.webdl.otus.socialnetwork.core.author.entities.User;

import java.util.List;
import java.util.UUID;

@Repository
public class FriendRepositoryImpl implements FriendRepository {

    @Override
    public List<User> getFriends(UUID userId) {
        return List.of();
    }

}
