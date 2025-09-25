package ru.webdl.otus.socialnetwork.core.post.entities.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.webdl.otus.socialnetwork.core.post.entities.Post;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
public class PostImpl implements Post {
    private UUID postId;
    private UUID authorId;
    private String content;
    private OffsetDateTime created;

    public PostImpl(UUID authorId, String content) {
        this.authorId = authorId;
        this.content = content;
    }
}
