package ru.webdl.otus.socialnetwork.infra.user.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFindUseCase;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFriendsheepUseCase;
import ru.webdl.otus.socialnetwork.core.user.cases.exceptions.UserNotFoundException;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.infra.user.CurrentUser;
import ru.webdl.otus.socialnetwork.infra.user.rest.dto.FriendResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/v1/user/friend", "/api/v1/public/user/friend"})
class UserFriendsheepController {
    private final UserFindUseCase userFindUseCase;
    private final UserFriendsheepUseCase userFriendsheepUseCase;

    @GetMapping("/all")
    ResponseEntity<List<FriendResponse>> getFriends(@CurrentUser User user) {
        return ResponseEntity.ok(userFriendsheepUseCase.getFriends(user).stream()
                .map(FriendResponse::new)
                .toList()
        );
    }

    @GetMapping("/get/{userId}")
    ResponseEntity<List<FriendResponse>> getFriends(@PathVariable UUID userId) {
        Optional<User> user = userFindUseCase.findById(userId);
        return user.map(value -> ResponseEntity.ok(userFriendsheepUseCase.getFriends(value).stream()
                .map(FriendResponse::new)
                .toList())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add/{id}")
    void addFriend(@CurrentUser User user, @PathVariable UUID id) {
        User friend = getUser(id);
        userFriendsheepUseCase.add(user, friend);
    }

    @DeleteMapping("/delete/{id}")
    void deleteFriend(@CurrentUser User user, @PathVariable UUID id) {
        User friend = getUser(id);
        userFriendsheepUseCase.delete(user, friend);
    }

    private User getUser(UUID id) {
        return userFindUseCase.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Не укзалось найти пользователя с ID " + id));
    }
}
