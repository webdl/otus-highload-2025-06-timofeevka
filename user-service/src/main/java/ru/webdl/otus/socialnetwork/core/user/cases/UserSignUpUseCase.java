package ru.webdl.otus.socialnetwork.core.user.cases;

import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.util.UUID;

public interface UserSignUpUseCase {

    UUID signup(User user);

}
