package ru.webdl.otus.socialnetwork.core.user.cases;

import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserFindUseCase {

    Optional<User> findById(UUID id);

    Optional<User> findByUsername(String username);

    List<User> findByFirstLastName(String firstName, String lastName);
}
