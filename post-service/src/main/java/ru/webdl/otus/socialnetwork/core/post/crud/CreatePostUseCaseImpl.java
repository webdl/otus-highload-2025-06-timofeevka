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
import ru.webdl.otus.socialnetwork.core.user.User;

@Service
@RequiredArgsConstructor
class CreatePostUseCaseImpl implements CreatePostUseCase {
    private final PostRepository postRepository;
    private final IncrementTotalPostsUseCase incrementTotalPostsUseCase;
    private final AuthorRepository authorRepository;
    private final CreateAuthorUseCase createAuthorUseCase;

    @Override
    @Transactional
    public Post create(User user, String content) {
        Author author = createAuthorUseCase.createIfNotExists(user);
        author = authorRepository.findByIdWithLock(author.getAuthorId()).orElseThrow();
        PostImpl post = new PostImpl(author.getAuthorId(), content);
        Post result = postRepository.create(post);
        incrementTotalPostsUseCase.incrementTotalPosts(author);
        return result;
    }
}
