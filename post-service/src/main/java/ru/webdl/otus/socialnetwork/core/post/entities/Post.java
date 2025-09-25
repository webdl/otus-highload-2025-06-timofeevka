package ru.webdl.otus.socialnetwork.core.post.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface Post {

    UUID getPostId();

    UUID getAuthorId();

    String getContent();

    OffsetDateTime getCreated();

}
