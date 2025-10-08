package ru.webdl.otus.socialnetwork.infra.post;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.post.PostEditUseCase;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.UUID;

@Primary
@Component
@RequiredArgsConstructor
public class CachePostEditUseCaseImpl implements PostEditUseCase {
    private final PostEditUseCase delegate;

    @Override
    @CacheEvict(value = "authorPosts", key = "#user.userId()", cacheManager = "userFeedCacheManager")
    public void update(User user, UUID postId, String content) {
        delegate.update(user, postId, content);
    }
}
