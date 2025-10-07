package ru.webdl.otus.socialnetwork.core.post;

import ru.webdl.otus.socialnetwork.core.user.User;

public interface PostCreationUseCase {
    Post create(User user, String content);
}
