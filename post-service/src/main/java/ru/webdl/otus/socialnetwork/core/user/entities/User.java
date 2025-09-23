package ru.webdl.otus.socialnetwork.core.user.entities;

import java.sql.Timestamp;
import java.util.UUID;

public interface User {

    UUID getId();

    String getDisplayName();

    int getTotalPosts();

    Timestamp getCreated();

    String getStatus();

}
