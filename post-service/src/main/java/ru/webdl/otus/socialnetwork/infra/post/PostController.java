package ru.webdl.otus.socialnetwork.infra.post;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.post.FindPostsUseCase;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.infra.post.dto.CreatePostRequest;
import ru.webdl.otus.socialnetwork.infra.post.dto.PostResponse;
import ru.webdl.otus.socialnetwork.infra.rest.UuidDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final CreatePostService createPostService;
    private final FindPostsUseCase findPostsUseCase;

    @PostMapping("/create")
    ResponseEntity<UuidDTO> create(@RequestBody CreatePostRequest postData) {
        Post post = createPostService.create(postData.getAuthorId(), postData.getContent());
        return ResponseEntity.ok(new UuidDTO(post.getPostId()));
    }

    @GetMapping("/get/{postId}")
    ResponseEntity<PostResponse> getPost(@PathVariable UUID postId) {
        return findPostsUseCase.findById(postId)
                .map(PostResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/feed/{userId}")
    @Cacheable(value = "userFeed", key = "#userId", unless = "#result == null || #result.body.isEmpty()")
    public ResponseEntity<List<PostResponse>> getFeed(@PathVariable UUID userId) {
        return ResponseEntity.ok(
                findPostsUseCase.getFriendsPosts(userId).stream()
                        .map(PostResponse::new)
                        .toList()
        );
    }
}
