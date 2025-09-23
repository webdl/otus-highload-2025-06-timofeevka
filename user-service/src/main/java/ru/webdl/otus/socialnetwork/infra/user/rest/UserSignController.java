package ru.webdl.otus.socialnetwork.infra.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.webdl.otus.socialnetwork.core.user.cases.UserSignUpUseCase;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.UserDTO;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.UserTokenDTO;

import java.util.UUID;

@RestController
class UserSignController {
    private final UserSignUpUseCase userSignUpUseCase;

    @Autowired
    UserSignController(UserSignUpUseCase userSignUpUseCase) {
        this.userSignUpUseCase = userSignUpUseCase;
    }

    @PostMapping("/register")
    ResponseEntity<UserTokenDTO> register(@RequestBody UserDTO user) {
        UUID userId = userSignUpUseCase.signup(user.toDomain());
        return ResponseEntity.ok(new UserTokenDTO(userId));
    }

}
