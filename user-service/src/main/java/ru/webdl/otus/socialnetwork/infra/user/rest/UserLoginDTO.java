package ru.webdl.otus.socialnetwork.infra.user.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserLoginDTO {
    private String username;
    private String password;
}
