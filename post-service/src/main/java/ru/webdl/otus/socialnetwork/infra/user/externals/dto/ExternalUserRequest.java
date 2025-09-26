package ru.webdl.otus.socialnetwork.infra.user.externals.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ExternalUserRequest {
    private UUID id;
    private String firstName;
    private String lastName;
}
