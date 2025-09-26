package ru.webdl.otus.socialnetwork.infra.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFindUseCase;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.infra.user.CurrentUser;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.UserResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
class UserInfoController {

    @GetMapping("/me")
    ResponseEntity<UserResponse> getCurrentUser(@CurrentUser User user) {
        return ResponseEntity.ok(new UserResponse(user));
    }

}
