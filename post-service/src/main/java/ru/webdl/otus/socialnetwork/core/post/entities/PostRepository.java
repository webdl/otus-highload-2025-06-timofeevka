package ru.webdl.otus.socialnetwork.core.post.entities;

import ru.webdl.otus.socialnetwork.core.user.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository {

    UUID create(Post post);

    void update(Post post);

    void delete(Post post);

    Optional<Post> getPost(UUID postId);

    List<Post> getPosts(List<User> users);
}
