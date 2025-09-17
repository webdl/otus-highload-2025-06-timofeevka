package ru.webdl.otus.socialnetwork.infra.security;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
public class TestBcryptMatchesPerformance {

    @Test
    void testBcryptMatchesPerformance() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        String password = "userPassword123";
        long start = System.currentTimeMillis();
        String hash = encoder.encode(password);
        long end = System.currentTimeMillis();
        log.info("Performance test: hash={}, time={}", hash, end - start);

        int iterations = 10;
        for (int i = 0; i < iterations; i++) {
            start = System.currentTimeMillis();
            boolean result = encoder.matches(password, hash);
            end = System.currentTimeMillis();
            log.info("{}: {}ms", iterations, end - start);
            assertTrue(result);
        }
    }
}
