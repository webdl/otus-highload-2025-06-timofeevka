package ru.webdl.otus.socialnetwork.infra.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.post.author.AuthorPostsFeedUseCase;
import ru.webdl.otus.socialnetwork.core.post.crud.CreatePostUseCase;
import ru.webdl.otus.socialnetwork.core.post.friend.FriendsPostsFeedUseCase;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.core.user.UserRepository;
import ru.webdl.otus.socialnetwork.infra.post.dto.CreatePostRequest;
import ru.webdl.otus.socialnetwork.infra.post.dto.PostResponse;
import ru.webdl.otus.socialnetwork.infra.rest.UuidDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final CreatePostUseCase createPostUseCase;
    private final AuthorPostsFeedUseCase authorPostsFeedUseCase;
    private final FriendsPostsFeedUseCase friendsPostsFeedUseCase;
    private final UserRepository userRepository;

    @PostMapping("/create")
    ResponseEntity<UuidDTO> create(@RequestHeader("userId") String userId,
                                   @RequestBody CreatePostRequest postData) {
        User user = getUser(userId);
        Post post = createPostUseCase.create(user, postData.getContent());
        return ResponseEntity.ok(new UuidDTO(post.getPostId()));
    }

    @GetMapping("/get/{postId}")
    ResponseEntity<PostResponse> getPost(@PathVariable UUID postId) {
        return authorPostsFeedUseCase.findById(postId)
                .map(PostResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/feed/")
    public ResponseEntity<List<PostResponse>> getFeed(@RequestHeader("userId") String userId) {
        User user = getUser(userId);
        return ResponseEntity.ok(
                friendsPostsFeedUseCase.getFriendsPosts(user).stream()
                        .map(PostResponse::new)
                        .toList()
        );
    }

    private User getUser(String userId) {
        UUID uuid = UUID.fromString(userId);
        return userRepository.getBy(uuid);
    }
}
