package ru.webdl.otus.socialnetwork.core.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.post.PostRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostsFeedByAuthorUseCaseImpl implements PostsFeedByAuthorUseCase {
    private final PostRepository postRepository;

    @Override
    public List<Post> getPosts(Author author) {
        return postRepository.getPosts(author.getAuthorId());
    }
}
