package ru.webdl.otus.socialnetwork.core.post.cases;

import ru.webdl.otus.socialnetwork.core.post.entities.Post;

import java.util.List;
import java.util.UUID;

public interface FriendPostsFeedUseCase {

    List<Post> getFriendsPosts(UUID userId);

}
