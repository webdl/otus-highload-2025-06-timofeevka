package ru.webdl.otus.socialnetwork.core.feed;

import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.post.Post;

import java.util.List;

public interface AuthorPostsUseCase {
    List<Post> getAuthorPosts(Author author);
}
