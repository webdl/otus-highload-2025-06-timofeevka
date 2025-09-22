package ru.webdl.otus.socialnetwork.infra.user.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.UserImpl;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String interests;
    private Integer cityId;
    private String username;
    private String password;

    User toDomain() {
        return new UserImpl(null, firstName, lastName, birthDate, gender, interests, cityId, username, password);
    }
}
