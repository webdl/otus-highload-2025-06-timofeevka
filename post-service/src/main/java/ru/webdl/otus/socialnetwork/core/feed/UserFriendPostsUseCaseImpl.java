package ru.webdl.otus.socialnetwork.core.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.author.ResolvePostAuthorUseCase;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.post.PostRepository;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFriendPostsUseCaseImpl implements UserFriendPostsUseCase {
    private final ResolvePostAuthorUseCase resolvePostAuthorUseCase;
    private final PostRepository postRepository;

    @Override
    public List<Post> getFriendPosts(User user) {
        return resolvePostAuthorUseCase.findFriendsWhoAreAuthors(user).stream()
                .flatMap(author -> getAuthorPosts(author).stream())
                .toList();
    }

    @Override
    public List<Post> getAuthorPosts(Author author) {
        return postRepository.getPosts(author.getAuthorId());
    }
}
