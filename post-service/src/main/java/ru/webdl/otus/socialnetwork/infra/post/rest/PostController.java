package ru.webdl.otus.socialnetwork.infra.post.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.post.cases.PostCreateUseCase;
import ru.webdl.otus.socialnetwork.core.post.entities.Post;
import ru.webdl.otus.socialnetwork.core.post.entities.impl.PostImpl;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.cases.CreateUserUseCase;
import ru.webdl.otus.socialnetwork.infra.post.rest.dto.PostCreateDTO;
import ru.webdl.otus.socialnetwork.infra.post.rest.dto.PostDTO;
import ru.webdl.otus.socialnetwork.infra.rest.UuidDTO;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostCreateUseCase postCreateUseCase;
    private final CreateUserUseCase createUserUseCase;

    @PostMapping("/create")
    ResponseEntity<UuidDTO> create(@RequestBody PostCreateDTO postData) {
        Optional<User> author = createUserUseCase.findById(postData.getAuthorId());
        if(author.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = new PostImpl(postData.getAuthorId(), postData.getContent());
        UUID postId = postCreateUseCase.create(post);
        return ResponseEntity.ok(new UuidDTO(postId));
    }

    @GetMapping("/get/{postId}")
    ResponseEntity<PostDTO> getPost(@PathVariable UUID postId) {
        return postCreateUseCase.findById(postId)
                .map(PostDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
