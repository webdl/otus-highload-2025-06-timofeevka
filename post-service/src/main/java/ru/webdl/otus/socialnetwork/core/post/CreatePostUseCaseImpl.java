package ru.webdl.otus.socialnetwork.core.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.author.CreateAuthorUseCase;
import ru.webdl.otus.socialnetwork.core.author.IncrementTotalPostsUseCase;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class CreatePostUseCaseImpl implements CreatePostUseCase {
    private final PostRepository postRepository;
    private final CreateAuthorUseCase createAuthorUseCase;
    private final IncrementTotalPostsUseCase incrementTotalPostsUseCase;

    @Override
    @Transactional
    public Post create(Author author, String content) {
        PostImpl post = new PostImpl(author.getAuthorId(), content);
        incrementTotalPostsUseCase.incrementTotalPosts(author);
        return postRepository.create(post);
    }

    @Override
    public void update(UUID postId, String content) {
        Post post = findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        if (!post.getContent().equals(content)) {
            post.setContent(content);
            postRepository.update(post);
        }
    }

    @Override
    public void delete(UUID postId) {
        postRepository.delete(postId);
    }

    @Override
    public Optional<Post> findById(UUID postId) {
        return postRepository.getPost(postId);
    }
}
