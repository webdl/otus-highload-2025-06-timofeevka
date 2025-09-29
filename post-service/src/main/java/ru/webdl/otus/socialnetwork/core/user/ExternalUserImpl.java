package ru.webdl.otus.socialnetwork.core.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ExternalUserImpl implements ExternalUser {
    @NonNull
    private final UUID userId;
    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;
}
