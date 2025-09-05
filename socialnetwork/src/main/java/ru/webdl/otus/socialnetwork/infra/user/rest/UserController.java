package ru.webdl.otus.socialnetwork.infra.user.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.user.UserService;
import ru.webdl.otus.socialnetwork.core.user.cases.UserRegistrationUseCaseImpl;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRegistrationUseCaseImpl userRegistrationUseCase;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserRegistrationUseCaseImpl userRegistrationUseCase,
                          UserService userService,
                          AuthenticationManager authenticationManager) {
        this.userRegistrationUseCase = userRegistrationUseCase;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserViewDTO> register(@RequestBody UserDTO user) {
        userRegistrationUseCase.register(user.toDomain());
        return userService.findByUsername(user.getUsername())
                .map(UserViewDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO user) {
        var token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication auth = authenticationManager.authenticate(token);
        if (auth.isAuthenticated()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserViewDTO> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(UserViewDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
