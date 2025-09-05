package ru.webdl.otus.socialnetwork.core.user.cases;

import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.PasswordHasher;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.UserRepository;

@Service
public class UserRegistrationUseCaseImpl implements UserRegistrationUseCase {
    private final UserRepository repository;
    private final PasswordHasher passwordHasher;


    public UserRegistrationUseCaseImpl(UserRepository repository, PasswordHasher passwordHasher) {
        this.repository = repository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public void register(User user) {
        user.setPassword(passwordHasher.encode(user.getPassword()));
        repository.create(user);
    }

}
