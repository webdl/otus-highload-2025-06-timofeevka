package ru.webdl.otus.socialnetwork.core.user.entities.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class UserImpl implements User {
    @ToString.Include
    private UUID id;
    @ToString.Include
    private String firstName;
    @ToString.Include
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String interests;
    private Integer cityId;
    @ToString.Include
    private String username;
    private String password;

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
