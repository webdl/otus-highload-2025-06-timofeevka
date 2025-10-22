package ru.webdl.otus.socialnetwork.infra.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserIdResponse {
    private UUID userId;
}
