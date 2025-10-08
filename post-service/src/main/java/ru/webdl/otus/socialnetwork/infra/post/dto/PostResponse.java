package ru.webdl.otus.socialnetwork.infra.post.dto;

import ru.webdl.otus.socialnetwork.core.post.Post;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PostResponse(UUID postId, UUID authorId, String content, OffsetDateTime created) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getUserId(),
                post.getContent(),
                post.getCreated());
    }
}
