package ru.webdl.otus.socialnetwork.infra.user.rest.dto;

import lombok.Data;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.impl.UserImpl;

import java.time.LocalDate;

@Data
public class UserSignUpDTO {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String interests;
    private Integer cityId;
    private String username;
    private String password;

    public User toDomain() {
        return new UserImpl(null, firstName, lastName, birthDate, gender, interests, cityId, username, password);
    }
}
