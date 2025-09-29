package ru.webdl.otus.socialnetwork.core.user;

import lombok.NonNull;

import java.util.UUID;

public record ExternalUserImpl(
        @NonNull UUID userId,
        @NonNull String firstName,
        @NonNull String lastName
) implements ExternalUser {
}
