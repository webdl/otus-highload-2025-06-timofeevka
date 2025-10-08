package ru.webdl.otus.socialnetwork.core.member;

import lombok.NonNull;

import java.util.UUID;

public record MemberImpl(
        @NonNull UUID userId,
        @NonNull String displayName
) implements Member {
}
