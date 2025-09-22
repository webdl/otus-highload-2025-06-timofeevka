package ru.webdl.otus.socialnetwork.core.user.entities;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    List<User> findByFirstLastName(String firstName, String lastName);

    int create(User user);

}
