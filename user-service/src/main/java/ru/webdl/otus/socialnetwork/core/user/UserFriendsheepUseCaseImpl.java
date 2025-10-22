package ru.webdl.otus.socialnetwork.core.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class UserFriendsheepUseCaseImpl implements UserFriendsheepUseCase {
    private final UserRepository repository;

    public UserFriendsheepUseCaseImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void add(User user, User friend) {
        repository.addFriend(user, friend);
        repository.addFriend(friend, user);
    }

    @Override
    @Transactional
    public void delete(User user, User friend) {
        repository.deleteFriend(user, friend);
        repository.deleteFriend(friend, user);
    }

    @Override
    public List<User> getFriends(User user) {
        return repository.getFriends(user);
    }
}
