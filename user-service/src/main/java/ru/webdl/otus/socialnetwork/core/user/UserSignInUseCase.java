package ru.webdl.otus.socialnetwork.core.user;

public interface UserSignInUseCase {
    User signin(String username, String password);
}
