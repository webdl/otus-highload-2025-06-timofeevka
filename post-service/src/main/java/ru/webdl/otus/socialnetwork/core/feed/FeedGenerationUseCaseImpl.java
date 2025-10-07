package ru.webdl.otus.socialnetwork.core.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedGenerationUseCaseImpl implements FeedGenerationUseCase {
    private final UserFriendPostsUseCase userFriendPostsUseCase;

    @Override
    public List<Post> getFeed(User user) {
        return userFriendPostsUseCase.getFriendPosts(user).stream()
                .sorted(Comparator.reverseOrder())
                .limit(1000)
                .toList();
    }
}
