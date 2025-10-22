package ru.webdl.otus.socialnetwork.core.user;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public interface User {
    UUID getId();

    String getFirstName();

    String getLastName();

    LocalDate getBirthDate();

    String getGender();

    String getInterests();

    Integer getCityId();

    String getUsername();

    String getPassword();

    OffsetDateTime getLastLogin();

    void setLastLogin(OffsetDateTime lastLogin);

    interface UserBuilder {
        UserBuilder id(UUID id);

        UserBuilder firstName(String firstName);

        UserBuilder lastName(String lastName);

        UserBuilder birthDate(LocalDate birthDate);

        UserBuilder gender(String gender);

        UserBuilder interests(String interests);

        UserBuilder cityId(Integer cityId);

        UserBuilder username(String username);

        UserBuilder password(String password);

        User build();
    }
}
