package ru.webdl.otus.socialnetwork.core.author.cases;

import ru.webdl.otus.socialnetwork.core.author.entities.Author;

import java.util.Optional;
import java.util.UUID;

public interface CreateAuthorUseCase {

    Optional<Author> findById(UUID authorId);

    Author createIfNotExists(UUID authorId);
}
