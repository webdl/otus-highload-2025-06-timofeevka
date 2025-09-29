package ru.webdl.otus.socialnetwork.core.author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository {
    Optional<Author> findById(UUID authorId);

    List<Author> getAuthors(List<UUID> authorIds);

    Author create(Author author);

    void save(Author author);
}
