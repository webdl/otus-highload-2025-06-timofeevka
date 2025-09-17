package ru.webdl.otus.socialnetwork.infra.user.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.user.UserService;
import ru.webdl.otus.socialnetwork.core.user.cases.UserSignUpUseCaseImpl;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
public class UserController {
    private final UserSignUpUseCaseImpl userRegistrationUseCase;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserSignUpUseCaseImpl userRegistrationUseCase,
                          UserService userService,
                          AuthenticationManager authenticationManager) {
        this.userRegistrationUseCase = userRegistrationUseCase;
        this.userService = userService;
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

    @PostMapping("/user/register")
    public ResponseEntity<UserViewDTO> register(@RequestBody UserDTO user) {
        userRegistrationUseCase.signup(user.toDomain());
        return userService.findByUsername(user.getUsername())
                .map(UserViewDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/get/{id}")
    public ResponseEntity<UserViewDTO> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(UserViewDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/find")
    public ResponseEntity<List<UserViewDTO>> findUsers(@RequestParam String firstName, @RequestParam String lastName) {
        long startTotal = System.currentTimeMillis();
        long startDb = System.currentTimeMillis();
        List<User> users = userService.findByFirstLastName(firstName, lastName);
        long dbTime = System.currentTimeMillis() - startDb;
        long startMapping = System.currentTimeMillis();
        List<UserViewDTO> usersDto = users.stream()
                .map(UserViewDTO::new)
                .collect(Collectors.toList());
        long mappingTime = System.currentTimeMillis() - startMapping;
        long totalTime = System.currentTimeMillis() - startTotal;
        log.debug("DB: {}ms, Mapping: {}ms, Total: {}ms", dbTime, mappingTime, totalTime);
//        List<UserViewDTO> usersDto = userService.findByFirstLastName(firstName, lastName).stream().map(UserViewDTO::new).toList();
        return ResponseEntity.ok(usersDto);
    }
}
