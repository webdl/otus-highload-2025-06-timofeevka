package ru.webdl.otus.socialnetwork.core.author;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface Author {
    UUID getAuthorId();

    String getDisplayName();

    Integer getTotalPosts();

    OffsetDateTime getCreated();

    String getStatus();
}
