package ru.webdl.otus.socialnetwork.core.user.cases;

import ru.webdl.otus.socialnetwork.core.user.entities.User;

public interface UserSignInUseCase {

    User signin(String username, String password);

}
