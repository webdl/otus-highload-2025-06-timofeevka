package ru.webdl.otus.socialnetwork.infra.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.post.CreatePostUseCase;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.infra.CreatePostService;
import ru.webdl.otus.socialnetwork.infra.post.dto.CreatePostRequest;
import ru.webdl.otus.socialnetwork.infra.post.dto.PostResponse;
import ru.webdl.otus.socialnetwork.infra.rest.UuidDTO;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final CreatePostUseCase createPostUseCase;
    private final CreatePostService createPostService;

    @PostMapping("/create")
    ResponseEntity<UuidDTO> create(@RequestBody CreatePostRequest postData) {
        Post post = createPostService.create(postData.getAuthorId(), postData.getContent());
        return ResponseEntity.ok(new UuidDTO(post.getPostId()));
    }

    @GetMapping("/get/{postId}")
    ResponseEntity<PostResponse> getPost(@PathVariable UUID postId) {
        return createPostUseCase.findById(postId)
                .map(PostResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
