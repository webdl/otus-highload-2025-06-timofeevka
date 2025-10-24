package ru.webdl.otus.socialnetwork.core.user;

import java.util.List;

public interface UserFriendsheepUseCase {
    void add(User user, User friend);

    void delete(User user, User friend);

    List<User> getFriends(User user, Boolean activeOnly);
}
