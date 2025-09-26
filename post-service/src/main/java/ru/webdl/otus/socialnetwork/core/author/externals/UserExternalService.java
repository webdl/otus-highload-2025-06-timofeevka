package ru.webdl.otus.socialnetwork.core.author.externals;

import ru.webdl.otus.socialnetwork.core.author.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserExternalService {

    Optional<User> findById(UUID userId);

}
