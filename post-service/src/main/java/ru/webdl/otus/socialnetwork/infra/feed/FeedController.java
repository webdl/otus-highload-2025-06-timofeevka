package ru.webdl.otus.socialnetwork.infra.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.webdl.otus.socialnetwork.core.feed.FeedGenerationUseCase;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.core.user.UserRepository;
import ru.webdl.otus.socialnetwork.infra.post.dto.PostResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class FeedController {
    private final FeedGenerationUseCase feedGenerationUseCase;
    private final UserRepository userRepository;

    @GetMapping("/feed")
    public ResponseEntity<List<PostResponse>> getFeed(@RequestHeader("userId") String userId) {
        return ResponseEntity.ok(feedGenerationUseCase.getFeed(getUser(userId)).stream()
                .map(PostResponse::from)
                .toList());
    }

    private User getUser(String userId) {
        UUID uuid = UUID.fromString(userId);
        return userRepository.getBy(uuid);
    }
}
