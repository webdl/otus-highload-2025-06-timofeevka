package ru.webdl.otus.socialnetwork.core.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
class UserFriendsheepUseCaseImpl implements UserFriendsheepUseCase {
    @Value("${app.friends.inactivityThresholdDays}")
    private Long INACTIVITY_THRESHOLD_DAYS;

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
    public List<User> getFriends(User user, Boolean activeOnly) {
        if (activeOnly != null && activeOnly) {
            return repository.findFriendsByLastLoginAfter(user, calcInactivityThresholdDate());
        }
        return repository.getFriends(user);
    }

    private OffsetDateTime calcInactivityThresholdDate() {
        return OffsetDateTime.now(ZoneOffset.UTC).minusDays(INACTIVITY_THRESHOLD_DAYS);
    }
}
