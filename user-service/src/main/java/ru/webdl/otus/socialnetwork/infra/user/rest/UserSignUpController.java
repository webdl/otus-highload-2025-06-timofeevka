package ru.webdl.otus.socialnetwork.infra.user.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.webdl.otus.socialnetwork.core.user.cases.UserSignUpUseCase;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.UserIdResponse;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.UserSignUpResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
class UserSignUpController {
    private final UserSignUpUseCase userSignUpUseCase;

    @PostMapping("/register")
    ResponseEntity<UserIdResponse> register(@RequestBody UserSignUpResponse user) {
        UUID userId = userSignUpUseCase.signup(user.toDomain());
        return ResponseEntity.ok(new UserIdResponse(userId));
    }

}
