package ru.webdl.otus.socialnetwork.infra.user.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserViewDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String interests;
    private Integer cityId;
    private String username;

    UserViewDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
        this.gender = user.getGender();
        this.interests = user.getInterests();
        this.cityId = user.getCityId();
        this.username = user.getUsername();
    }
}
