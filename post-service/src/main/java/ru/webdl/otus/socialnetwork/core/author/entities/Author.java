package ru.webdl.otus.socialnetwork.core.author.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface Author {

    UUID getAuthorId();

    String getDisplayName();

    int getTotalPosts();

    OffsetDateTime getCreated();

    String getStatus();

}
