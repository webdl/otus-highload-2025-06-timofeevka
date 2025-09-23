package ru.webdl.otus.socialnetwork.core.user.entities.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
public class UserImpl implements User {
    private UUID id;
    private String displayName;
    private int totalPosts;
    private Timestamp created;
    private String status;
}
