package ru.webdl.otus.socialnetwork.core.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class PostDeleteUseCaseImpl implements PostDeleteUseCase {
    private final PostRepository postRepository;

    @Override
    public void delete(UUID postId) {
        postRepository.delete(postId);
    }
}
