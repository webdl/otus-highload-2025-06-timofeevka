package ru.webdl.otus.socialnetwork.infra.user;

import java.time.LocalDate;
import java.util.UUID;

public record UserRecord(
        UUID userIdd,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String gender,
        String interests,
        Integer cityId,
        String username,
        String password
) {
}
