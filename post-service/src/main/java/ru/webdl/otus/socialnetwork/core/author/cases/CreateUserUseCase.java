package ru.webdl.otus.socialnetwork.core.author.cases;

import ru.webdl.otus.socialnetwork.core.author.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface CreateUserUseCase {

    Optional<User> findById(UUID userId);

    User createIfNotExists(UUID userId);
}
