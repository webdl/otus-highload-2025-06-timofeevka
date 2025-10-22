package ru.webdl.otus.socialnetwork.infra.user.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserSignUpResponse {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String interests;
    private Integer cityId;
    private String username;
    private String password;
}
