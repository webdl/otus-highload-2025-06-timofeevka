package ru.webdl.otus.socialnetwork.core.user.cases;

import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.entities.User;
import ru.webdl.otus.socialnetwork.core.user.entities.UserRepository;

import java.util.List;

@Service
public class UserFriendsheepUseCaseImpl implements UserFriendsheepUseCase {
    private final UserRepository repository;

    public UserFriendsheepUseCaseImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(User user, User friend) {
        repository.addFriend(user, friend);
    }

    @Override
    public void delete(User user, User friend) {
        repository.deleteFriend(user, friend);
    }

    @Override
    public List<User> getFriends(User user) {
        return repository.getFriends(user);
    }
}
