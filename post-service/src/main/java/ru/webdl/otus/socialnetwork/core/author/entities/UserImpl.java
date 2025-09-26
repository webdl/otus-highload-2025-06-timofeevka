package ru.webdl.otus.socialnetwork.core.author.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
public class UserImpl implements User {
    private UUID userId;
    private String displayName;
    private int totalPosts;
    private OffsetDateTime created;
    private String status;

    public UserImpl(UUID userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }
}
