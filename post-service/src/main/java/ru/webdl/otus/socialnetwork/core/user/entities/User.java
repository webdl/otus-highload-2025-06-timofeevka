package ru.webdl.otus.socialnetwork.core.user.entities;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface User {

    UUID getId();

    String getDisplayName();

    int getTotalPosts();

    ZonedDateTime getCreated();

    String getStatus();

}
