package ru.webdl.otus.socialnetwork.infra.user.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFindUseCase;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFriendsheepUseCase;
import ru.webdl.otus.socialnetwork.core.user.cases.exceptions.UserNotFoundException;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.infra.user.CurrentUser;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.FriendDTO;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/friend")
class UserFriendsheepController {
    private final UserFindUseCase userFindUseCase;
    private final UserFriendsheepUseCase userFriendsheepUseCase;

    @Autowired
    UserFriendsheepController(UserFindUseCase userFindUseCase,
                              UserFriendsheepUseCase userFriendsheepUseCase) {
        this.userFindUseCase = userFindUseCase;
        this.userFriendsheepUseCase = userFriendsheepUseCase;
    }

    @GetMapping("/all")
    ResponseEntity<List<FriendDTO>> getFriends(@CurrentUser User user) {
        return ResponseEntity.ok(userFriendsheepUseCase.getFriends(user).stream()
                .map(FriendDTO::new)
                .toList()
        );
    }

    @PostMapping("/add/{id}")
    void addFriend(@CurrentUser User user, @PathVariable UUID id) {
        User friend = getUser(id);
        userFriendsheepUseCase.add(user, friend);
    }

    @DeleteMapping("delete/{id}")
    void deleteFriend(@CurrentUser User user, @PathVariable UUID id) {
        User friend = getUser(id);
        userFriendsheepUseCase.delete(user, friend);
    }

    private User getUser(UUID id) {
        return userFindUseCase.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Не укзалось найти пользователя с ID " + id));
    }
}
