package ru.webdl.otus.socialnetwork.core.post;

import java.util.List;
import java.util.UUID;

public interface FriendPostsFeedUseCase {
    List<Post> getFriendsPosts(UUID userId);
}
