package ru.webdl.otus.socialnetwork.core.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.post.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorPostsUseCaseImpl implements AuthorPostsUseCase {
    private final PostRepository postRepository;

    @Override
    public List<Post> getAuthorPosts(Author author) {
        return postRepository.getPosts(author.getAuthorId());
    }
}
