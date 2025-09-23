package ru.webdl.otus.socialnetwork.infra.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFindUseCase;
import ru.webdl.otus.socialnetwork.core.user.cases.UserSignUpUseCase;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.infra.user.CurrentUser;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.UserDTO;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.UserSignUpDTO;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.UserTokenDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
class UserController {
    private final UserFindUseCase userFindUseCase;
    private final UserSignUpUseCase userSignUpUseCase;

    @Autowired
    UserController(UserFindUseCase userFindUseCase,
                   UserSignUpUseCase userSignUpUseCase) {
        this.userFindUseCase = userFindUseCase;
        this.userSignUpUseCase = userSignUpUseCase;
    }

    @PostMapping("/register")
    ResponseEntity<UserTokenDTO> register(@RequestBody UserSignUpDTO user) {
        UUID userId = userSignUpUseCase.signup(user.toDomain());
        return ResponseEntity.ok(new UserTokenDTO(userId));
    }


    @GetMapping("/me")
    ResponseEntity<UserDTO> getCurrentUser(@CurrentUser User user) {
        return ResponseEntity.ok(new UserDTO(user));
    }

    @GetMapping("/get/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return userFindUseCase.findById(id)
                .map(UserDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/find")
    ResponseEntity<List<UserDTO>> findUsers(@RequestParam String firstName, @RequestParam String lastName) {
        return ResponseEntity.ok(
                userFindUseCase.findByFirstLastName(firstName, lastName).stream()
                        .map(UserDTO::new)
                        .toList()
        );
    }
}
