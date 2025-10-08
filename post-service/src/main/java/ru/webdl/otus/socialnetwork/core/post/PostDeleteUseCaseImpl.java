package ru.webdl.otus.socialnetwork.core.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class PostDeleteUseCaseImpl implements PostDeleteUseCase {
    private final PostRepository postRepository;

    @Override
    public void delete(User user, UUID postId) {
        postRepository.delete(postId);
    }
}
