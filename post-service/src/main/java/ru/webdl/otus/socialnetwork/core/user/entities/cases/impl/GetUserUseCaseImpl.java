package ru.webdl.otus.socialnetwork.core.user.entities.cases.impl;

import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.UserRepository;
import ru.webdl.otus.socialnetwork.core.user.entities.cases.GetUserUseCase;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetUserUseCaseImpl implements GetUserUseCase {
    private final UserRepository userRepository;

    public GetUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public UUID create(User user) {
        return userRepository.create(user);
    }
}
