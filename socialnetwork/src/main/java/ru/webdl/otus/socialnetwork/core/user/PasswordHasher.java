package ru.webdl.otus.socialnetwork.core.user;

public interface PasswordHasher {

    String encode(String password);

    boolean verify(String rawPassword, String encodedPassword);

}
