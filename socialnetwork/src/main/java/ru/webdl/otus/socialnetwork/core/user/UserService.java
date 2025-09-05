package ru.webdl.otus.socialnetwork.core.user;

import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);
}
