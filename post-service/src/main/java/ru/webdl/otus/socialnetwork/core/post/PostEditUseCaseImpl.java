package ru.webdl.otus.socialnetwork.core.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class PostEditUseCaseImpl implements PostEditUseCase {
    private final PostRepository postRepository;

    @Override
    public void update(UUID postId, String content) {
        Post post = postRepository.getPost(postId).orElseThrow(() -> new PostNotFoundException(postId));
        if (!post.getContent().equals(content)) {
            post.setContent(content);
            postRepository.update(post);
        }
    }
}
