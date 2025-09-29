package ru.webdl.otus.socialnetwork.core.post;

import ru.webdl.otus.socialnetwork.core.author.Author;

import java.util.Optional;
import java.util.UUID;

public interface CreatePostUseCase {
    Post create(Author author, String content);

    void update(UUID postId, String content);

    void delete(UUID postId);

    Optional<Post> findById(UUID postId);
}
