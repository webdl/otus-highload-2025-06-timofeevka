package ru.webdl.otus.socialnetwork.core.feed;

import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.post.Post;

import java.util.List;

public interface PostsFeedByAuthorUseCase {
    List<Post> getPosts(Author author);
}
