package ru.webdl.otus.socialnetwork.core.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.User;

@Service
@RequiredArgsConstructor
class CreateAuthorUseCaseImpl implements CreateAuthorUseCase {
    private final AuthorRepository authorRepository;

    @Override
    public Author createIfNotExists(User user) {
        return authorRepository.findById(user.userId())
                .orElseGet(() -> createAuthorFromExternalService(user));
    }

    private Author createAuthorFromExternalService(User user) {
        String displayName = user.firstName() + " " + user.lastName();
        AuthorImpl author = new AuthorImpl(user.userId(), displayName);
        return authorRepository.create(author);
    }
}
