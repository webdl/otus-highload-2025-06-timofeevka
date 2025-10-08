package ru.webdl.otus.socialnetwork.core.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.author.ResolveAuthorsUseCase;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedGenerationUseCaseImpl implements FeedGenerationUseCase {
    private final AuthorPostsUseCase authorPostsUseCase;
    private final ResolveAuthorsUseCase resolveAuthorsUseCase;

    @Override
    public List<Post> getFeed(User user) {
        return getFriends(user).stream()
                .flatMap(author -> authorPostsUseCase.getAuthorPosts(author).stream())
                .sorted(Comparator.reverseOrder())
                .limit(1000)
                .toList();
    }

    private List<Author> getFriends(User user) {
        return resolveAuthorsUseCase.findFriendsWhoAreAuthors(user);
    }
}
