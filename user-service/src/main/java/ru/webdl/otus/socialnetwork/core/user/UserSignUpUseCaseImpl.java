package ru.webdl.otus.socialnetwork.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.util.PasswordHasher;

@Service
@RequiredArgsConstructor
class UserSignUpUseCaseImpl implements UserSignUpUseCase {
    private final UserRepository repository;
    private final PasswordHasher passwordHasher;

    @Override
    public User signup(UserCreateParameters p) {
        User user = UserImpl.create(p.getFirstName(), p.getLastName(), p.getUsername(), passwordHasher.encode(p.getPassword()))
                .birthDate(p.getBirthDate())
                .gender(p.getGender())
                .interests(p.getInterests())
                .cityId(p.getCityId())
                .build();
        return repository.create(user);
    }
}
