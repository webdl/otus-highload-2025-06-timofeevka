package ru.webdl.otus.socialnetwork.infra.user.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RemoteUserRequest {
    private UUID id;
    private String firstName;
    private String lastName;
}
