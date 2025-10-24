package ru.webdl.otus.socialnetwork.infra.user.dto;

import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.UUID;

public record UserIdResponse(UUID userId) {
    public static UserIdResponse from(User u) {
        return new UserIdResponse(u.getId());
    }
}
