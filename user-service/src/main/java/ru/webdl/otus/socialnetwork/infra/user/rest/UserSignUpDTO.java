package ru.webdl.otus.socialnetwork.infra.user.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserSignUpDTO {
    private UUID userId;
}
