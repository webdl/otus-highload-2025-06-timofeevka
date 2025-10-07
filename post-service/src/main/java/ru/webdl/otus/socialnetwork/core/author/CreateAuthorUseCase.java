package ru.webdl.otus.socialnetwork.core.author;

import ru.webdl.otus.socialnetwork.core.user.User;

public interface CreateAuthorUseCase {
    Author createIfNotExists(User user);
}
