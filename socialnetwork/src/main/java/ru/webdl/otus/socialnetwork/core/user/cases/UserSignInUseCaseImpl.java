package ru.webdl.otus.socialnetwork.core.user.cases;

import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.PasswordHasher;
import ru.webdl.otus.socialnetwork.core.user.UserService;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

@Service
public class UserSignInUseCaseImpl implements UserSignInUseCase {
    private final PasswordHasher passwordHasher;
    private final UserService userService;

    public UserSignInUseCaseImpl(PasswordHasher passwordHasher, UserService userService) {
        this.passwordHasher = passwordHasher;
        this.userService = userService;
    }

    @Override
    public User signin(String username, String password) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
        if (!passwordHasher.matches(password, user.getPassword())) {
            throw new PasswordIncorrectException("exception.badCredentials");
        }
        return user;
    }

}
