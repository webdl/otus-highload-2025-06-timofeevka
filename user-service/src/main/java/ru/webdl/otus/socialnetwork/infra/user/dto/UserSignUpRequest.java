package ru.webdl.otus.socialnetwork.infra.user.dto;

import java.time.LocalDate;

public record UserSignUpRequest(
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
