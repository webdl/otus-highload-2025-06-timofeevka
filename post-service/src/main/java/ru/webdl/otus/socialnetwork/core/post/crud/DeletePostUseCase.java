package ru.webdl.otus.socialnetwork.core.post.crud;

import java.util.UUID;

public interface DeletePostUseCase {
    void delete(UUID postId);
}
