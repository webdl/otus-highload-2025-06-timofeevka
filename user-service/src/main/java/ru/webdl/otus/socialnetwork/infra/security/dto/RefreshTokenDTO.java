package ru.webdl.otus.socialnetwork.infra.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RefreshTokenDTO {
    @NotBlank
    @Schema(example = "refreshToken", description = "Refresh token")
    private String refreshToken;
}
