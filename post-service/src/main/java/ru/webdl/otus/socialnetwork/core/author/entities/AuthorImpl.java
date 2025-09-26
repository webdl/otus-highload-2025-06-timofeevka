package ru.webdl.otus.socialnetwork.core.author.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
public class AuthorImpl implements Author {
    private UUID authorId;
    private String displayName;
    private int totalPosts;
    private OffsetDateTime created;
    private String status;

    public AuthorImpl(UUID authorId, String displayName) {
        this.authorId = authorId;
        this.displayName = displayName;
    }
}
