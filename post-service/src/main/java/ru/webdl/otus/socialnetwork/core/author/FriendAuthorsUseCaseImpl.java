package ru.webdl.otus.socialnetwork.core.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.UserRepository;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendAuthorsUseCaseImpl implements FriendAuthorsUseCase {
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAuthors(User user) {
        List<User> userFriends = userRepository.getUserFriends(user);
        return authorRepository.getAuthors(userFriends.stream().map(User::userId).toList());
    }
}
