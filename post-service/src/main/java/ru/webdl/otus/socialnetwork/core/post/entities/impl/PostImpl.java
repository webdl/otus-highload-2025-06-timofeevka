package ru.webdl.otus.socialnetwork.core.post.entities.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.webdl.otus.socialnetwork.core.post.entities.Post;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
public class PostImpl implements Post {
    private UUID id;
    private User author;
    private String content;
    private ZonedDateTime created;
}
