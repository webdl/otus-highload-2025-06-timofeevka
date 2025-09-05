package ru.webdl.otus.socialnetwork.core.user.entities;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    void create(User user);

}
