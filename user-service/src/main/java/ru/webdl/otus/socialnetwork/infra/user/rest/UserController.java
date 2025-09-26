package ru.webdl.otus.socialnetwork.infra.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFindUseCase;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.UserResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/api/v1/user", "/api/v1/public/user"})
class UserController {
    private final UserFindUseCase userFindUseCase;

    @Autowired
    UserController(UserFindUseCase userFindUseCase) {
        this.userFindUseCase = userFindUseCase;
    }

    @GetMapping("/get/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        return userFindUseCase.findById(id)
                .map(UserResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/find")
    ResponseEntity<List<UserResponse>> findUsers(@RequestParam String firstName, @RequestParam String lastName) {
        return ResponseEntity.ok(
                userFindUseCase.findByFirstLastName(firstName, lastName).stream()
                        .map(UserResponse::new)
                        .toList()
        );
    }
}
