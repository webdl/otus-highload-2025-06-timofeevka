package ru.webdl.otus.socialnetwork.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserSignUpUseCaseImpl implements UserSignUpUseCase {
    private final UserRepository repository;
    private final PasswordHasher passwordHasher;

    @Override
    public User signup(UserCreateParameters parameters) {
        User user = UserImpl.create(parameters.getFirstName(), parameters.getLastName(), parameters.getUsername())
                .birthDate(parameters.getBirthDate())
                .gender(parameters.getGender())
                .interests(parameters.getInterests())
                .cityId(parameters.getCityId())
                .password(passwordHasher.encode(parameters.getPassword()))
                .build();
        return repository.create(user);
    }
}
