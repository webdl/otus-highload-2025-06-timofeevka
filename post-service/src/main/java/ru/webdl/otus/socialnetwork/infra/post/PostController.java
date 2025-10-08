package ru.webdl.otus.socialnetwork.infra.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.webdl.otus.socialnetwork.core.post.*;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.core.user.UserRepository;
import ru.webdl.otus.socialnetwork.infra.post.dto.PostCreationRequest;
import ru.webdl.otus.socialnetwork.infra.post.dto.PostResponse;
import ru.webdl.otus.socialnetwork.infra.post.dto.PostEditRequest;
import ru.webdl.otus.socialnetwork.infra.post.dto.UuidResponse;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostCreationUseCase postCreationUseCase;
    private final PostDeleteUseCase postDeleteUseCase;
    private final PostEditUseCase postEditUseCase;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable UUID postId) {
        return postRepository.getPost(postId)
                .map(post -> ResponseEntity.ok(PostResponse.from(post)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<UuidResponse> create(@RequestHeader("userId") String userId,
                                        @RequestBody PostCreationRequest postData) {
        Post post = postCreationUseCase.create(getUser(userId), postData.content());
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/posts/{id}")
                .buildAndExpand(post.getPostId())
                .toUri();
        return ResponseEntity.created(location).body(new UuidResponse(post.getPostId()));
    }

    @PutMapping("/{postId}")
    @ResponseStatus(code = HttpStatus.OK)
    void edit(@RequestHeader("userId") String userId,
              @PathVariable UUID postId,
              @RequestBody PostEditRequest postData) {
        postEditUseCase.update(getUser(userId), postId, postData.content());
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@RequestHeader("userId") String userId,
                @PathVariable UUID postId) {
        postDeleteUseCase.delete(getUser(userId), postId);
    }

    private User getUser(String userId) {
        UUID uuid = UUID.fromString(userId);
        return userRepository.getBy(uuid);
    }
}
