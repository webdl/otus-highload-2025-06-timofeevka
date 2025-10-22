package ru.webdl.otus.socialnetwork.infra.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.infra.user.dto.UserResponse;

@RestController
@RequestMapping("/api/v1/user")
class UserInfoController {

    @GetMapping("/me")
    ResponseEntity<UserResponse> getCurrentUser(@CurrentUser User user) {
        return ResponseEntity.ok(new UserResponse(user));
    }

}
