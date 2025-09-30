package ru.webdl.otus.socialnetwork.core.post.author;

import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.post.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorPostsFeedUseCase {
    Optional<Post> findById(UUID postId);

    List<Post> getByAuthor(Author author);
}
