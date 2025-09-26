package ru.webdl.otus.socialnetwork.core.user.externals;

import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserExternalService {

    Optional<User> findById(UUID userId);

}
