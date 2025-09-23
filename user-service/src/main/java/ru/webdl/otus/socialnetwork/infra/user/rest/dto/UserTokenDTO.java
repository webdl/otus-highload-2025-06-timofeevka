package ru.webdl.otus.socialnetwork.infra.user.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserTokenDTO {
    private UUID userId;
}
