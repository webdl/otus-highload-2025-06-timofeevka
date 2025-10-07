package ru.webdl.otus.socialnetwork.infra.post.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.post.PostCreationUseCase;
import ru.webdl.otus.socialnetwork.core.user.User;

@Primary
@Component
@RequiredArgsConstructor
public class CachePostCreationUseCase implements PostCreationUseCase {
    private final PostCreationUseCase delegate;

    @Override
    @CacheEvict(value = "authorPosts", key = "#user.userId()", cacheManager = "userFeedCacheManager")
    public Post create(User user, String content) {
        return delegate.create(user, content);
    }
}
