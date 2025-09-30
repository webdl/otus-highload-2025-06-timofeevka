package ru.webdl.otus.socialnetwork.core.post.crud;

import java.util.UUID;

public interface UpdatePostUseCase {
    void update(UUID postId, String content);
}
