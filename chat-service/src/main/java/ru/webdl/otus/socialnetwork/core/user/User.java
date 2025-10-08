package ru.webdl.otus.socialnetwork.core.user;

import java.util.UUID;

public interface User {
    UUID userId();

    String firstName();

    String lastName();
}
