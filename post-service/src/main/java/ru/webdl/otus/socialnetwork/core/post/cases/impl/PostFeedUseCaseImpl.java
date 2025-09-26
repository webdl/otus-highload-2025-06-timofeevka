package ru.webdl.otus.socialnetwork.core.post.cases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.post.cases.PostFeedUseCase;
import ru.webdl.otus.socialnetwork.core.post.entities.Post;
import ru.webdl.otus.socialnetwork.core.post.entities.PostRepository;
import ru.webdl.otus.socialnetwork.core.author.repositories.FriendRepository;
import ru.webdl.otus.socialnetwork.core.author.entities.User;

import java.util.List;
import java.util.UUID;

@Service
class PostFeedUseCaseImpl implements PostFeedUseCase {
    private final FriendRepository friendRepository;
    private final PostRepository postRepository;

    @Autowired
    PostFeedUseCaseImpl(FriendRepository friendRepository, PostRepository postRepository) {
        this.friendRepository = friendRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getFriendsPosts(UUID userId) {
        List<User> friends = friendRepository.getFriends(userId);
        return postRepository.getPosts(friends);
    }
}
