package ru.webdl.otus.socialnetwork.infra.user.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserTokenDTO {
    private UUID userId;
}
