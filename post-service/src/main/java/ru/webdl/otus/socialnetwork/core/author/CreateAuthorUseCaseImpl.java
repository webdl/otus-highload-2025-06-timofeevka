package ru.webdl.otus.socialnetwork.core.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.core.user.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class CreateAuthorUseCaseImpl implements CreateAuthorUseCase {
    private final AuthorRepository authorRepository;
    private final UserService userService;

    @Override
    public Author createIfNotExists(UUID authorId) {
        return authorRepository.findById(authorId)
                .orElseGet(() -> createAuthorFromExternalService(authorId));
    }

    private Author createAuthorFromExternalService(UUID userId) {
        User user = userService.findById(userId);
        String displayName = user.firstName() + " " + user.lastName();
        AuthorImpl author = new AuthorImpl(user.userId(), displayName);
        return authorRepository.create(author);
    }
}
