package ru.webdl.otus.socialnetwork.core.user;

import lombok.NonNull;

import java.util.UUID;

public record UserImpl(
        @NonNull UUID userId,
        @NonNull String firstName,
        @NonNull String lastName
) implements User {
}
