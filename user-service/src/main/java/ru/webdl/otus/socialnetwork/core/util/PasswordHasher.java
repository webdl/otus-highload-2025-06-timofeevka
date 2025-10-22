package ru.webdl.otus.socialnetwork.core.util;

public interface PasswordHasher {
    String encode(String password);

    boolean matches(String rawPassword, String encodedPassword);
}
