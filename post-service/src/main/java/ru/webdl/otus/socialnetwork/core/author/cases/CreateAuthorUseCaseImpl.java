package ru.webdl.otus.socialnetwork.core.author.cases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.author.entities.Author;
import ru.webdl.otus.socialnetwork.core.author.exceptions.UserNotFoundException;
import ru.webdl.otus.socialnetwork.core.author.externals.UserExternalService;
import ru.webdl.otus.socialnetwork.core.author.repositories.AuthorRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAuthorUseCaseImpl implements CreateAuthorUseCase {
    private final AuthorRepository authorRepository;
    private final UserExternalService userExternalService;

    @Override
    public Optional<Author> findById(UUID authorId) {
        return authorRepository.findById(authorId);
    }

    @Override
    public Author createIfNotExists(UUID authorId) {
        return authorRepository.findById(authorId)
                .orElseGet(() -> createAuthorFromExternalService(authorId));
    }

    private Author createAuthorFromExternalService(UUID userId) {
        Author authorFromService = userExternalService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return authorRepository.create(authorFromService);
    }
}
