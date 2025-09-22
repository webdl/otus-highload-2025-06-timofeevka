package ru.webdl.otus.socialnetwork.core.user.cases;

import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.PasswordHasher;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.UserRepository;

@Service
class UserSignUpUseCaseImpl implements UserSignUpUseCase {
    private final UserRepository repository;
    private final PasswordHasher passwordHasher;


    public UserSignUpUseCaseImpl(UserRepository repository, PasswordHasher passwordHasher) {
        this.repository = repository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public int signup(User user) {
        user.setPassword(passwordHasher.encode(user.getPassword()));
        return repository.create(user);
    }
}
