package ru.webdl.otus.socialnetwork.infra.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;

import java.util.Date;

public record ErrorResponse(
        @Schema(description = "Error message", example = "Error message") @NotBlank String message,
        @Schema(description = """
                Error code: \
                1 - General error (HTTP: 500 - Internal Server Error), \
                2 - Authentication failed (HTTP: 401 - Unauthorized), \
                3 - JWT token expired (HTTP: 401 - Unauthorized), \
                10 - Bad request parameters (HTTP: 400 - Bad Request), \
                20 - Access denied (HTTP: 403 - Forbidden)""",
                example = "2") @NotBlank ErrorCode code,
        @Schema(description = "Error code", example = "401") @NotBlank HttpStatus status,
        @Schema(description = "Error timestamp", example = "2021-08-25T15:00:00") @NotBlank Date timestamp) {

    public static ErrorResponse of(final String message, final ErrorCode code) {
        return new ErrorResponse(message, code, code.getStatus(), new Date());
    }
}
