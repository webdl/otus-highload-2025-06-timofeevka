package ru.webdl.otus.socialnetwork.core.post.crud;

import ru.webdl.otus.socialnetwork.core.post.Post;

import java.util.UUID;

public interface CreatePostUseCase {
    Post create(UUID userId, String content);
}
