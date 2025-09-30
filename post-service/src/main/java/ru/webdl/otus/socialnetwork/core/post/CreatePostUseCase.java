package ru.webdl.otus.socialnetwork.core.post;

import java.util.UUID;

public interface CreatePostUseCase {
    Post create(UUID userId, String content);

    void update(UUID postId, String content);

    void delete(UUID postId);
}
