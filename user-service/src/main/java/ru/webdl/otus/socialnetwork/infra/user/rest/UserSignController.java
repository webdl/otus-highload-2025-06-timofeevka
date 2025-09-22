package ru.webdl.otus.socialnetwork.infra.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.webdl.otus.socialnetwork.core.user.cases.UserSignUpUseCase;

import java.util.UUID;

@RestController
public class UserSignController {
    private final UserSignUpUseCase userSignUpUseCase;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserSignController(UserSignUpUseCase userSignUpUseCase,
                              AuthenticationManager authenticationManager) {
        this.userSignUpUseCase = userSignUpUseCase;
        this.authenticationManager = authenticationManager;
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

    @PostMapping("/register")
    public ResponseEntity<UserSignUpDTO> register(@RequestBody UserDTO user) {
        UUID userId = userSignUpUseCase.signup(user.toDomain());
        return ResponseEntity.ok(new UserSignUpDTO(userId));
    }

}
