package ru.webdl.otus.socialnetwork.infra.post;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.feed.AuthorPostsUseCase;

import java.util.List;

@Primary
@Component
@RequiredArgsConstructor
public class CacheAuthorPostsUseCaseImpl implements AuthorPostsUseCase {
    private final AuthorPostsUseCase delegate;

    @Override
    @Cacheable(value = "authorPosts", key = "#author.authorId", cacheManager = "userFeedCacheManager")
    public List<Post> getAuthorPosts(Author author) {
        return delegate.getAuthorPosts(author);
    }
}
