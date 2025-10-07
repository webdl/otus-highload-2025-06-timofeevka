package ru.webdl.otus.socialnetwork.infra.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.feed.FeedGenerationUseCase;
import ru.webdl.otus.socialnetwork.core.post.PostCreationUseCase;
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
    private final PostCreationUseCase postCreationUseCase;
    private final FeedGenerationUseCase feedGenerationUseCase;
    private final UserRepository userRepository;

    @PostMapping("/create")
    ResponseEntity<UuidDTO> create(@RequestHeader("userId") String userId,
                                   @RequestBody CreatePostRequest postData) {
        Post post = postCreationUseCase.create(getUser(userId), postData.getContent());
        return ResponseEntity.ok(new UuidDTO(post.getPostId()));
    }

    @GetMapping("/feed/")
    public ResponseEntity<List<PostResponse>> getFeed(@RequestHeader("userId") String userId) {
        return ResponseEntity.ok(feedGenerationUseCase.getFeed(getUser(userId)).stream()
                .map(PostResponse::new)
                .toList());
    }

    private User getUser(String userId) {
        UUID uuid = UUID.fromString(userId);
        return userRepository.getBy(uuid);
    }
}
