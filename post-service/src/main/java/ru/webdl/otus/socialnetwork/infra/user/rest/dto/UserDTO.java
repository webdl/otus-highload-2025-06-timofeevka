package ru.webdl.otus.socialnetwork.infra.user.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.impl.UserImpl;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String displayName;
    private int totalPosts;
    private OffsetDateTime created;
    private String status;

    public UserDTO(User user) {
        this.id = user.getUserId();
        this.displayName = user.getDisplayName();
        this.totalPosts = user.getTotalPosts();
        this.created = user.getCreated();
        this.status = user.getStatus();
    }

    public User toDomain() {
        return new UserImpl(id, displayName, totalPosts, created, status);
    }
}
