package ru.webdl.otus.socialnetwork.core.post;

import java.util.UUID;

public interface PostEditUseCase {
    void update(UUID postId, String content);
}
