package ru.webdl.otus.socialnetwork.core.user;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findById(UUID id);

    Optional<User> findByUsername(String username);

    List<User> findByFirstLastName(String firstName, String lastName);

    User create(User user);

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);

    List<User> getFriends(User user);

    List<User> findFriendsByLastLoginAfter(User user, OffsetDateTime date);

    void updateLastLogin(User user);
}
