package ru.webdl.otus.socialnetwork.core.author;

import java.util.List;
import java.util.UUID;

public interface FriendAuthorsUseCase {
    List<Author> getAuthors(UUID userId);
}
