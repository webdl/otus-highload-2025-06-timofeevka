package ru.webdl.otus.socialnetwork.infra.user.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFindUseCase;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RestController("/user")
public class UserController {
    private final UserFindUseCase userFindUseCase;

    @Autowired
    public UserController(UserFindUseCase userFindUseCase) {
        this.userFindUseCase = userFindUseCase;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserViewDTO> getUserById(@PathVariable UUID id) {
        return userFindUseCase.findById(id)
                .map(UserViewDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/find")
    public ResponseEntity<List<UserViewDTO>> findUsers(@RequestParam String firstName, @RequestParam String lastName) {
        long startTotal = System.currentTimeMillis();
        long startDb = System.currentTimeMillis();
        List<User> users = userFindUseCase.findByFirstLastName(firstName, lastName);
        long dbTime = System.currentTimeMillis() - startDb;
        long startMapping = System.currentTimeMillis();
        List<UserViewDTO> usersDto = users.stream()
                .map(UserViewDTO::new)
                .collect(Collectors.toList());
        long mappingTime = System.currentTimeMillis() - startMapping;
        long totalTime = System.currentTimeMillis() - startTotal;
        log.debug("DB: {}ms, Mapping: {}ms, Total: {}ms", dbTime, mappingTime, totalTime);
//        List<UserViewDTO> usersDto = userFindUseCase.findByFirstLastName(firstName, lastName).stream().map(UserViewDTO::new).toList();
        return ResponseEntity.ok(usersDto);
    }
}
