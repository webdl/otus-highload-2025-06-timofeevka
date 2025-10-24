package ru.webdl.otus.socialnetwork.infra.user.dto;

import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.UUID;

public record FriendResponse(UUID id,
                             String firstName,
                             String lastName
) {
    public static FriendResponse from(User u) {
        return new FriendResponse(
                u.getId(),
                u.getFirstName(),
                u.getLastName()
        );
    }
}
