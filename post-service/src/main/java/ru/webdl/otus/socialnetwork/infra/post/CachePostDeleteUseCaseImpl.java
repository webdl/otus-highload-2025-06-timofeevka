package ru.webdl.otus.socialnetwork.infra.post;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.post.PostDeleteUseCase;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.UUID;

@Primary
@Component
@RequiredArgsConstructor
public class CachePostDeleteUseCaseImpl implements PostDeleteUseCase {
    private final PostDeleteUseCase delegate;

    @Override
    @CacheEvict(value = "authorPosts", key = "#user.userId()", cacheManager = "userFeedCacheManager")
    public void delete(User user, UUID postId) {
        delegate.delete(user, postId);
    }
}
