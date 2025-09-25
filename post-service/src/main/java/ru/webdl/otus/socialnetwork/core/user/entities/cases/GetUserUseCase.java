package ru.webdl.otus.socialnetwork.core.user.entities.cases;

import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface GetUserUseCase {

    Optional<User> findById(UUID id);

    UUID create(User user);

}
