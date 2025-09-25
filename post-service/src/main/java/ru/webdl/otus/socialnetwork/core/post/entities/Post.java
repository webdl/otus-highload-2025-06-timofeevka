package ru.webdl.otus.socialnetwork.core.post.entities;

import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface Post {

    UUID getId();

    User getAuthor();

    String getContent();

    ZonedDateTime getCreated();

}
