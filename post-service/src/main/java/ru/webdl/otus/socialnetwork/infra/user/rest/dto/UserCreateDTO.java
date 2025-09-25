package ru.webdl.otus.socialnetwork.infra.user.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.impl.UserImpl;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserCreateDTO {
    private UUID id;
    private String displayName;

    public User toDomain() {
        return new UserImpl(id, displayName);
    }
}
