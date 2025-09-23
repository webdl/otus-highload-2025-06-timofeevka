package ru.webdl.otus.socialnetwork.core.user.entities;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findById(UUID id);

    UUID create(User user);

}
