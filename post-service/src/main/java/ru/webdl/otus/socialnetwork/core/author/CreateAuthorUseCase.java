package ru.webdl.otus.socialnetwork.core.author;

import java.util.UUID;

public interface CreateAuthorUseCase {
    Author createIfNotExists(UUID authorId);
}
