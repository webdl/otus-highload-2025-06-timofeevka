package ru.webdl.otus.socialnetwork.core.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.author.AuthorRepository;
import ru.webdl.otus.socialnetwork.core.author.IncrementTotalPostsUseCase;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class CreatePostUseCaseImpl implements CreatePostUseCase {
    private final PostRepository postRepository;
    private final IncrementTotalPostsUseCase incrementTotalPostsUseCase;
    private final FindPostsUseCase findPostsUseCase;
    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public Post create(UUID userId, String content) {
        Author author = authorRepository.findByIdWithLock(userId).orElseThrow();
        PostImpl post = new PostImpl(userId, content);
        Post result = postRepository.create(post);
        incrementTotalPostsUseCase.incrementTotalPosts(author);
        return result;
    }

    @Override
    public void update(UUID postId, String content) {
        Post post = findPostsUseCase.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        if (!post.getContent().equals(content)) {
            post.setContent(content);
            postRepository.update(post);
        }
    }

    @Override
    public void delete(UUID postId) {
        postRepository.delete(postId);
    }
}
