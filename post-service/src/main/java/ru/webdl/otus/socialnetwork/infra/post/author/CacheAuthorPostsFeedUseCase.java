package ru.webdl.otus.socialnetwork.infra.post.author;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.post.author.AuthorPostsFeedUseCase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Component
@RequiredArgsConstructor
public class CacheAuthorPostsFeedUseCase implements AuthorPostsFeedUseCase {
    private final AuthorPostsFeedUseCase delegate;

    @Override
    public Optional<Post> findById(UUID postId) {
        return delegate.findById(postId);
    }

    @Override
    @Cacheable(value = "authorPosts", key = "#author.authorId", cacheManager = "userFeedCacheManager")
    public List<Post> getByAuthor(Author author) {
        return delegate.getByAuthor(author);
    }
}
