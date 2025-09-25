package ru.webdl.otus.socialnetwork.core.post.cases;

import ru.webdl.otus.socialnetwork.core.post.entities.Post;

import java.util.Optional;
import java.util.UUID;

public interface PostCrudUseCase {

    UUID create(Post post);

    void update(Post post);

    void delete(Post post);

    Optional<Post> findById(UUID id);

}
