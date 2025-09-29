package ru.webdl.otus.socialnetwork.core.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.author.FriendAuthorsUseCase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class FindPostsUseCaseImpl implements FindPostsUseCase {
    private final FriendAuthorsUseCase friendAuthorsUseCase;
    private final PostRepository postRepository;


    @Override
    public Optional<Post> findById(UUID postId) {
        return postRepository.getPost(postId);
    }

    @Override
    public List<Post> getFriendsPosts(UUID userId) {
        List<Author> authors = friendAuthorsUseCase.getAuthors(userId);
        return postRepository.getPosts(authors);
    }
}
