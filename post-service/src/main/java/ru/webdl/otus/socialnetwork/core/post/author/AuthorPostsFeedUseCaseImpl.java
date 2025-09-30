package ru.webdl.otus.socialnetwork.core.post.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.post.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthorPostsFeedUseCaseImpl implements AuthorPostsFeedUseCase {
    private final PostRepository postRepository;


    @Override
    public Optional<Post> findById(UUID postId) {
        return postRepository.getPost(postId);
    }

    @Override
    public List<Post> getByAuthor(Author author) {
        return postRepository.getPosts(author.getAuthorId());
    }
}
