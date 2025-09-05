package ru.webdl.otus.socialnetwork.core.user.entities;

import java.time.LocalDate;

public interface User {

    Long getId();

    String getFirstName();

    String getLastName();

    LocalDate getBirthDate();

    String getGender();

    String getInterests();

    Integer getCityId();

    String getUsername();

    String getPassword();

    void setPassword(String password);

}
