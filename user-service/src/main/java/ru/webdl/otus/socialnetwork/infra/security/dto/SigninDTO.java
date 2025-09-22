package ru.webdl.otus.socialnetwork.infra.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SigninDTO {
    @NotBlank
    @Schema(example = "username", description = "User name")
    private String username;

    @NotBlank
    @Schema(example = "password", description = "User password")
    private String password;
}
