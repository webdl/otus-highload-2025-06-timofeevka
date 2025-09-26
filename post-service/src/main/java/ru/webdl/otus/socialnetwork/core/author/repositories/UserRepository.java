package ru.webdl.otus.socialnetwork.core.author.repositories;

import ru.webdl.otus.socialnetwork.core.author.entities.Author;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<Author> findById(UUID id);

    Author create(Author author);

}
