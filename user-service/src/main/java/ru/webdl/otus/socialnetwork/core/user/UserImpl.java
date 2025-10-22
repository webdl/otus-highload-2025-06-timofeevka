package ru.webdl.otus.socialnetwork.core.user;

import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserImpl implements User {
    private final UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String interests;
    private Integer cityId;
    private final String username;
    private String password;
    @Setter
    private OffsetDateTime lastLogin;

    static User.UserBuilder create(@NonNull String firstName,
                                   @NonNull String lastName,
                                   @NonNull String username,
                                   @NonNull String password) {
        return UserImpl.builder()
                .id(UUID.randomUUID())
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password);
    }

    public static class UserImplBuilder implements User.UserBuilder {
        public User build() {
            Objects.requireNonNull(id);
            Objects.requireNonNull(firstName);
            Objects.requireNonNull(lastName);
            Objects.requireNonNull(username);
            Objects.requireNonNull(password);
            return new UserImpl(id, firstName, lastName, birthDate, gender, interests, cityId, username, password, lastLogin);
        }
    }
}
