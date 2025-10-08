package ru.webdl.otus.socialnetwork.core.post;

import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.UUID;

public interface PostEditUseCase {
    void update(User user, UUID postId, String content);
}
