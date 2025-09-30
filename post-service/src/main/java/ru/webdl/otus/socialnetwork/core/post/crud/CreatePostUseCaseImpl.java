package ru.webdl.otus.socialnetwork.core.post.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.author.AuthorRepository;
import ru.webdl.otus.socialnetwork.core.author.CreateAuthorUseCase;
import ru.webdl.otus.socialnetwork.core.author.IncrementTotalPostsUseCase;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.post.PostImpl;
import ru.webdl.otus.socialnetwork.core.post.PostRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class CreatePostUseCaseImpl implements CreatePostUseCase {
    private final PostRepository postRepository;
    private final IncrementTotalPostsUseCase incrementTotalPostsUseCase;
    private final AuthorRepository authorRepository;
    private final CreateAuthorUseCase createAuthorUseCase;

    @Override
    @Transactional
    public Post create(UUID userId, String content) {
        // TODO: Разобраться с этим
        Author author = createAuthorUseCase.createIfNotExists(userId);
        author = authorRepository.findByIdWithLock(userId).orElseThrow();
        PostImpl post = new PostImpl(userId, content);
        Post result = postRepository.create(post);
        incrementTotalPostsUseCase.incrementTotalPosts(author);
        return result;
    }
}
