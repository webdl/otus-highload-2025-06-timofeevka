package ru.webdl.otus.socialnetwork.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.util.PasswordHasher;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
class UserSignInUseCaseImpl implements UserSignInUseCase {
    private final PasswordHasher passwordHasher;
    private final UserFindUseCase userFindUseCase;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User signin(String username, String password) {
        User user = userFindUseCase.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
        if (!passwordHasher.matches(password, user.getPassword())) {
            throw new PasswordIncorrectException("exception.badCredentials");
        }
        user.setLastLogin(OffsetDateTime.now(ZoneOffset.UTC));
        userRepository.updateLastLogin(user);
        return user;
    }
}
