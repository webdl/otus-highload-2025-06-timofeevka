package ru.webdl.otus.socialnetwork.core.post.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.post.PostNotFoundException;
import ru.webdl.otus.socialnetwork.core.post.PostRepository;
import ru.webdl.otus.socialnetwork.core.post.author.AuthorPostsFeedUseCase;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class UpdatePostUseCaseImpl implements UpdatePostUseCase {
    private final PostRepository postRepository;
    private final AuthorPostsFeedUseCase authorPostsFeedUseCase;

    @Override
    public void update(UUID postId, String content) {
        Post post = authorPostsFeedUseCase.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        if (!post.getContent().equals(content)) {
            post.setContent(content);
            postRepository.update(post);
        }
    }
}
