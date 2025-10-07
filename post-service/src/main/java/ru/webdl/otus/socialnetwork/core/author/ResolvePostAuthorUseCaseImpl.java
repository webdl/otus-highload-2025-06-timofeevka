package ru.webdl.otus.socialnetwork.core.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.core.user.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class ResolvePostAuthorUseCaseImpl implements ResolvePostAuthorUseCase {
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Author getOrCreate(User user) {
        return authorRepository.findById(user.userId())
                .orElseGet(() -> createAuthorFromExternalService(user));
    }

    private Author createAuthorFromExternalService(User user) {
        String displayName = user.firstName() + " " + user.lastName();
        AuthorImpl author = new AuthorImpl(user.userId(), displayName);
        return authorRepository.create(author);
    }

    @Override
    public List<Author> findFriendsWhoAreAuthors(User user) {
        List<UUID> userFriendIds = userRepository.getFriendsFor(user).stream()
                .map(User::userId)
                .toList();
        return authorRepository.getAuthors(userFriendIds);
    }
}
