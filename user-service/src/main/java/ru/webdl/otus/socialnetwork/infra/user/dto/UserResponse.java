package ru.webdl.otus.socialnetwork.infra.user.dto;

import ru.webdl.otus.socialnetwork.core.user.User;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String gender,
        String interests,
        Integer cityId,
        String username,
        OffsetDateTime lastLogin
) {
    public static UserResponse from(User u) {
        return new UserResponse(
                u.getId(),
                u.getFirstName(),
                u.getLastName(),
                u.getBirthDate(),
                u.getGender(),
                u.getInterests(),
                u.getCityId(),
                u.getUsername(),
                u.getLastLogin()
        );
    }
}
