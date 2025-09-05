package ru.webdl.otus.socialnetwork.core.user.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserImpl implements User {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String interests;
    private Integer cityId;
    private String username;
    private String password;

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
