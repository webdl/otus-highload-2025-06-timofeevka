package ru.webdl.otus.socialnetwork.core.post.cases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.post.cases.PostCrudUseCase;
import ru.webdl.otus.socialnetwork.core.post.entities.Post;
import ru.webdl.otus.socialnetwork.core.post.entities.PostRepository;

import java.util.Optional;
import java.util.UUID;

@Service
class PostCrudUseCaseImpl implements PostCrudUseCase {
    private final PostRepository postRepository;

    @Autowired
    PostCrudUseCaseImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public UUID create(Post post) {
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
