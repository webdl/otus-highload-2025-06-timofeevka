package ru.webdl.otus.socialnetwork.core.post.cases.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.post.cases.PostCreateUseCase;
import ru.webdl.otus.socialnetwork.core.post.entities.Post;
import ru.webdl.otus.socialnetwork.core.post.entities.PostRepository;
import ru.webdl.otus.socialnetwork.core.user.cases.CreateUserUseCase;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class PostCreateUseCaseImpl implements PostCreateUseCase {
    private final PostRepository postRepository;
    private final CreateUserUseCase createUserUseCase;

    @Override
    public UUID create(Post post) {
        createUserUseCase.createIfNotExists(post.getAuthorId());
        return postRepository.create(post);
    }

    @Override
    public void update(Post post) {
        postRepository.update(post);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public Optional<Post> findById(UUID postId) {
        return postRepository.getPost(postId);
    }
}
