package ru.webdl.otus.socialnetwork.core.post.cases;

import ru.webdl.otus.socialnetwork.core.post.entities.Post;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.util.List;

public interface PostFeedUseCase {

    List<Post> getFriendsPosts(User user);

}
