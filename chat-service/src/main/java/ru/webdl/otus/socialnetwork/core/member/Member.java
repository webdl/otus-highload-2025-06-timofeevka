package ru.webdl.otus.socialnetwork.core.member;

import java.util.UUID;

public interface Member {
    UUID userId();

    String displayName();
}
