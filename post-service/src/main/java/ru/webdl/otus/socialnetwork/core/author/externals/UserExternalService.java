package ru.webdl.otus.socialnetwork.core.author.externals;

import ru.webdl.otus.socialnetwork.core.author.entities.Author;

import java.util.Optional;
import java.util.UUID;

public interface UserExternalService {

    Optional<Author> findById(UUID userId);

}
