package ru.webdl.otus.socialnetwork.infra.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.util.PasswordHasher;

@Component
@RequiredArgsConstructor
class PasswordHasherImpl implements PasswordHasher {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
