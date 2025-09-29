package ru.webdl.otus.socialnetwork.core.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.author.FriendRepository;
import ru.webdl.otus.socialnetwork.core.author.Author;

import java.util.List;
import java.util.UUID;

@Service
class FriendPostsFeedUseCaseImpl implements FriendPostsFeedUseCase {
    private final FriendRepository friendRepository;
    private final PostRepository postRepository;

    @Autowired
    FriendPostsFeedUseCaseImpl(FriendRepository friendRepository, PostRepository postRepository) {
        this.friendRepository = friendRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getFriendsPosts(UUID userId) {
        List<Author> friends = friendRepository.getFriends(userId);
        return postRepository.getPosts(friends);
    }
}
