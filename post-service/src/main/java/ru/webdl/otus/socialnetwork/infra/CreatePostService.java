package ru.webdl.otus.socialnetwork.infra;

import ru.webdl.otus.socialnetwork.core.post.Post;

import java.util.UUID;

public interface CreatePostService {
    Post create(UUID authorId, String content);
}
