package ru.webdl.otus.socialnetwork.core.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.core.user.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendAuthorsUseCaseImpl implements FriendAuthorsUseCase {
    private final UserService userService;
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAuthors(UUID userId) {
        List<User> userFriends = userService.findUserFriends(userId);
        return authorRepository.getAuthors(userFriends.stream().map(User::userId).toList());
    }
}
