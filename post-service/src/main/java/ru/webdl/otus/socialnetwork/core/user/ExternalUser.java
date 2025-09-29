package ru.webdl.otus.socialnetwork.core.user;

import java.util.UUID;

public interface ExternalUser {
    UUID getUserId();

    String getFirstName();

    String getLastName();
}
