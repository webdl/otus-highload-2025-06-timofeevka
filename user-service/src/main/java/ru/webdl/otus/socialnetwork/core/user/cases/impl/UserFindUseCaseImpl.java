package ru.webdl.otus.socialnetwork.core.user.cases.impl;

import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFindUseCase;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class UserFindUseCaseImpl implements UserFindUseCase {
    private final UserRepository repository;

    public UserFindUseCaseImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<User> findByFirstLastName(String firstName, String lastName) {
        return repository.findByFirstLastName(firstName, lastName);
    }
}
