package ru.webdl.otus.socialnetwork.infra.post.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.webdl.otus.socialnetwork.core.post.entities.Post;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PostDTO {
    private UUID postId;
    private UUID authorId;
    private String content;
    private OffsetDateTime created;

    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.authorId = post.getAuthorId();
        this.content = post.getContent();
        this.created = post.getCreated();
    }
}
