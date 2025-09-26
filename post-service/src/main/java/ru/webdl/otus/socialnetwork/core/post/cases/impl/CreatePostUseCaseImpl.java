package ru.webdl.otus.socialnetwork.core.post.cases.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.post.cases.CreatePostUseCase;
import ru.webdl.otus.socialnetwork.core.post.entities.Post;
import ru.webdl.otus.socialnetwork.core.post.entities.PostRepository;
import ru.webdl.otus.socialnetwork.core.author.cases.CreateAuthorUseCase;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class CreatePostUseCaseImpl implements CreatePostUseCase {
    private final PostRepository postRepository;
    private final CreateAuthorUseCase createAuthorUseCase;

    @Override
    public UUID create(Post post) {
        createAuthorUseCase.createIfNotExists(post.getAuthorId());
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
