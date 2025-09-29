package ru.webdl.otus.socialnetwork.core.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class AuthorImpl implements Author {
    private final UUID authorId;
    @NonNull
    private String displayName;
    private Integer totalPosts;
    private final OffsetDateTime created;
    private String status;

    AuthorImpl(UUID authorId, String displayName) {
        this(authorId, displayName, null, null, null);
    }

    void incrementTotalPosts() {
        this.totalPosts += 1;
    }
}
