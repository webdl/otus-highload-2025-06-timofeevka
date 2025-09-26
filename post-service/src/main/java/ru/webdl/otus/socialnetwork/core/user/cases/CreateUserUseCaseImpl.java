package ru.webdl.otus.socialnetwork.core.user.cases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.exceptions.UserNotFoundException;
import ru.webdl.otus.socialnetwork.core.user.externals.UserExternalService;
import ru.webdl.otus.socialnetwork.core.user.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final UserExternalService userExternalService;

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User createIfNotExists(UUID userId) {
        return userRepository.findById(userId)
                .orElseGet(() -> createUserFromExternalService(userId));
    }

    private User createUserFromExternalService(UUID userId) {
        User userFromService = userExternalService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userRepository.create(userFromService);
    }
}
