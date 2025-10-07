package ru.webdl.otus.socialnetwork.core.post;

import java.util.UUID;

public interface PostDeleteUseCase {
    void delete(UUID postId);
}
