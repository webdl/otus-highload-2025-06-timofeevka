package ru.webdl.otus.socialnetwork.infra.user.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.user.entities.cases.GetUserUseCase;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.UserCreateDTO;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.UserDTO;
import ru.webdl.otus.socialnetwork.infra.post.rest.dto.UuidDTO;

import java.util.UUID;

@RestController
@RequestMapping("/user")
class UserController {
    private final GetUserUseCase getUserUseCase;

    UserController(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    @PostMapping("/create")
    ResponseEntity<UuidDTO> create(@RequestBody UserCreateDTO user) {
        UUID userId = getUserUseCase.create(user.toDomain());
        return ResponseEntity.ok(new UuidDTO(userId));
    }

    @GetMapping("/get/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return getUserUseCase.findById(id)
                .map(UserDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
