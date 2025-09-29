package ru.webdl.otus.socialnetwork.core.author;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository {
    Optional<Author> findById(UUID authorId);

    Author create(Author author);

    void save(Author author);
}
