package ru.webdl.otus.socialnetwork.core.post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FindPostsUseCase {
    Optional<Post> findById(UUID postId);

    List<Post> getFriendsPosts(UUID userId);
}
