package ru.webdl.otus.socialnetwork.core.author;

import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.List;

public interface ResolvePostAuthorUseCase {
    Author getOrCreate(User user);

    List<Author> findFriendsWhoAreAuthors(User user);
}
