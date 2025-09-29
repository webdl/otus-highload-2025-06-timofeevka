package ru.webdl.otus.socialnetwork.core.post;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface Post {
    UUID getPostId();

    UUID getUserId();

    String getContent();

    void setContent(String content);

    OffsetDateTime getCreated();
}
