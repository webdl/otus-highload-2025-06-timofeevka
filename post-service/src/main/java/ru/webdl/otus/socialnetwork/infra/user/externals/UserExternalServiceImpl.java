package ru.webdl.otus.socialnetwork.infra.user.externals;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.externals.UserExternalService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserExternalServiceImpl implements UserExternalService {

    @Override
    public Optional<User> findById(UUID userId) {
        return Optional.empty();
    }

}
