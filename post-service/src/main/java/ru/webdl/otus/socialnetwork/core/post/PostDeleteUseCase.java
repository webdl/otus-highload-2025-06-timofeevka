package ru.webdl.otus.socialnetwork.core.post;

import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.UUID;

public interface PostDeleteUseCase {
    void delete(User user, UUID postId);
}
