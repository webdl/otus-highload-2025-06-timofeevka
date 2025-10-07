package ru.webdl.otus.socialnetwork.core.feed;

import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.List;

public interface UserFriendPostsUseCase {
    List<Post> getFriendPosts(User user);

    List<Post> getAuthorPosts(Author author);
}
