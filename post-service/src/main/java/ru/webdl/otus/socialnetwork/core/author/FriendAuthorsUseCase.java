package ru.webdl.otus.socialnetwork.core.author;

import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.List;

public interface FriendAuthorsUseCase {
    List<Author> getAuthors(User user);
}
