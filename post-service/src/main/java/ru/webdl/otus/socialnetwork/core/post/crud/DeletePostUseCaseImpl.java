package ru.webdl.otus.socialnetwork.core.post.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.post.PostRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class DeletePostUseCaseImpl implements DeletePostUseCase {
    private final PostRepository postRepository;

    @Override
    public void delete(UUID postId) {
        postRepository.delete(postId);
    }
}
