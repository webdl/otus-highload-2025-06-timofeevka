package ru.webdl.otus.socialnetwork.infra.user.rest.dto;

import lombok.Data;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String interests;
    private Integer cityId;
    private String username;

    public UserDTO(User user) {
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

