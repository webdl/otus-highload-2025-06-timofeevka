package ru.webdl.otus.socialnetwork.infra.user.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FriendDTO {
    UUID id;
    String firstName;
    String lastName;

    public FriendDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
