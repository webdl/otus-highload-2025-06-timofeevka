package ru.webdl.otus.socialnetwork.infra.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FriendResponse {
    UUID id;
    String firstName;
    String lastName;

    public FriendResponse(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
