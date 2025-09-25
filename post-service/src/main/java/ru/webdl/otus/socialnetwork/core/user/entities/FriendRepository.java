package ru.webdl.otus.socialnetwork.core.user.entities;

import java.util.List;

public interface FriendRepository {

    List<User> getFriends(User user);

}
