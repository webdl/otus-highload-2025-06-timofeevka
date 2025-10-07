package ru.webdl.otus.socialnetwork.core.feed;

import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.List;

public interface FeedGenerationUseCase {
    List<Post> getFeed(User user);
}
