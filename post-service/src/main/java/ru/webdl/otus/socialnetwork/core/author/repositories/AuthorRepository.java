package ru.webdl.otus.socialnetwork.core.author.repositories;

import ru.webdl.otus.socialnetwork.core.author.entities.Author;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository {

    Optional<Author> findById(UUID authorId);

    Author create(Author author);

}
