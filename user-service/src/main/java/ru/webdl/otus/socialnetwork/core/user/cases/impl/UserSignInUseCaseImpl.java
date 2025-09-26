package ru.webdl.otus.socialnetwork.core.user.cases.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.PasswordHasher;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFindUseCase;
import ru.webdl.otus.socialnetwork.core.user.cases.UserSignInUseCase;
import ru.webdl.otus.socialnetwork.core.user.cases.exceptions.PasswordIncorrectException;
import ru.webdl.otus.socialnetwork.core.user.cases.exceptions.UserNotFoundException;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

@Service
@RequiredArgsConstructor
class UserSignInUseCaseImpl implements UserSignInUseCase {
    private final PasswordHasher passwordHasher;
    private final UserFindUseCase userFindUseCase;

    @Override
    public User signin(String username, String password) {
        User user = userFindUseCase.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
        if (!passwordHasher.matches(password, user.getPassword())) {
            throw new PasswordIncorrectException("exception.badCredentials");
        }
        return user;
    }

}
