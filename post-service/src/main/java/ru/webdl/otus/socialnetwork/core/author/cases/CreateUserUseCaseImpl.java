package ru.webdl.otus.socialnetwork.core.author.cases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.author.entities.User;
import ru.webdl.otus.socialnetwork.core.author.exceptions.UserNotFoundException;
import ru.webdl.otus.socialnetwork.core.author.externals.UserExternalService;
import ru.webdl.otus.socialnetwork.core.author.repositories.UserRepository;

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
