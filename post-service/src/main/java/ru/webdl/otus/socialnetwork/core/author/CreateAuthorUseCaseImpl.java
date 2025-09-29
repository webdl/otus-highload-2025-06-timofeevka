package ru.webdl.otus.socialnetwork.core.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.ExternalUser;
import ru.webdl.otus.socialnetwork.core.user.ExternalUserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class CreateAuthorUseCaseImpl implements CreateAuthorUseCase {
    private final AuthorRepository authorRepository;
    private final ExternalUserService externalUserService;

    @Override
    public Author createIfNotExists(UUID authorId) {
        return authorRepository.findById(authorId)
                .orElseGet(() -> createAuthorFromExternalService(authorId));
    }

    private Author createAuthorFromExternalService(UUID userId) {
        ExternalUser externalUser = externalUserService.findById(userId);
        AuthorImpl author = new AuthorImpl(externalUser.userId(),
                externalUser.firstName() + " " + externalUser.lastName());
        return authorRepository.create(author);
    }
}
