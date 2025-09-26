package ru.webdl.otus.socialnetwork.core.user.repositories;

import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findById(UUID id);

    User create(User user);

}
