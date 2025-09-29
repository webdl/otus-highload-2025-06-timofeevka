package ru.webdl.otus.socialnetwork.infra.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.webdl.otus.socialnetwork.core.post.Post;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PostResponse {
    private UUID postId;
    private UUID authorId;
    private String content;
    private OffsetDateTime created;

    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.authorId = post.getUserId();
        this.content = post.getContent();
        this.created = post.getCreated();
    }
}
