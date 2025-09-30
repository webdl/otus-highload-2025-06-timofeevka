package ru.webdl.otus.socialnetwork.core.post;

import ru.webdl.otus.socialnetwork.core.author.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository {

    Post create(Post post);

    void update(Post post);

    void delete(UUID postId);

    Optional<Post> getPost(UUID postId);

    List<Post> getPosts(UUID authorId);

    List<Post> getPosts(List<Author> authors);
}
