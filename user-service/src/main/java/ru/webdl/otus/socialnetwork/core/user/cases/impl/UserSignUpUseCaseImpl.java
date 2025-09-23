package ru.webdl.otus.socialnetwork.core.user.cases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.PasswordHasher;
import ru.webdl.otus.socialnetwork.core.user.cases.UserSignUpUseCase;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.UserRepository;

import java.util.UUID;

@Service
class UserSignUpUseCaseImpl implements UserSignUpUseCase {
    private final UserRepository repository;
    private final PasswordHasher passwordHasher;

    @Autowired
    public UserSignUpUseCaseImpl(UserRepository repository, PasswordHasher passwordHasher) {
        this.repository = repository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public UUID signup(User user) {
        user.setPassword(passwordHasher.encode(user.getPassword()));
        return repository.create(user);
    }
}
