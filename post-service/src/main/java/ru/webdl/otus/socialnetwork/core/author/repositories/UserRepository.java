package ru.webdl.otus.socialnetwork.core.author.repositories;

import ru.webdl.otus.socialnetwork.core.author.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findById(UUID id);

    User create(User user);

}
