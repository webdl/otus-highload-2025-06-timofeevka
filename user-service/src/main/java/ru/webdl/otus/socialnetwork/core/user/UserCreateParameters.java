package ru.webdl.otus.socialnetwork.core.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreateParameters {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String interests;
    private Integer cityId;
    private String username;
    private String password;

    public static UserCreateParametersBuilder create(String firstName, String lastName, String username, String password) {
        return  UserCreateParameters.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password);
    }

    public static class UserCreateParametersBuilder {
        public UserCreateParameters build() {
            Objects.requireNonNull(firstName);
            Objects.requireNonNull(username);
            Objects.requireNonNull(password);
            return new UserCreateParameters(firstName, lastName, birthDate, gender, interests, cityId, username, password);
        }
    }
}
