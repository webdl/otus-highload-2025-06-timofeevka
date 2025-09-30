package ru.webdl.otus.socialnetwork.core.post.friend;

import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.List;

public interface FriendsPostsFeedUseCase {
    List<Post> getFriendsPosts(User user);
}
