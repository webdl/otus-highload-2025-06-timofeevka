package ru.webdl.otus.socialnetwork.core.post.crud;

import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.user.User;

public interface CreatePostUseCase {
    Post create(User user, String content);
}
