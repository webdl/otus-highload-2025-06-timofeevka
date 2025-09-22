package ru.webdl.otus.socialnetwork.core.user;

public interface PasswordHasher {

    String encode(String password);

    boolean matches(String rawPassword, String encodedPassword);

}
