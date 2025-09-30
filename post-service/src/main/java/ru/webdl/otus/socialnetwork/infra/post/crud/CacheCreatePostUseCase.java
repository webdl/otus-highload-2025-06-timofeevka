package ru.webdl.otus.socialnetwork.infra.post.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.post.crud.CreatePostUseCase;

import java.util.UUID;

@Primary
@Component
@RequiredArgsConstructor
public class CacheCreatePostUseCase implements CreatePostUseCase {
    private final CreatePostUseCase delegate;

    @Override
    @CacheEvict(value = "authorPosts", key = "#userId", cacheManager = "userFeedCacheManager")
    public Post create(UUID userId, String content) {
        return delegate.create(userId, content);
    }
}
