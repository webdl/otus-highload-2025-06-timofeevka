package ru.webdl.otus.socialnetwork.core.post.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.author.FriendAuthorsUseCase;
import ru.webdl.otus.socialnetwork.core.post.Post;
import ru.webdl.otus.socialnetwork.core.post.author.AuthorPostsFeedUseCase;
import ru.webdl.otus.socialnetwork.core.user.User;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class FriendsPostsFeedUseCaseImpl implements FriendsPostsFeedUseCase {
    private final FriendAuthorsUseCase friendAuthorsUseCase;
    private final AuthorPostsFeedUseCase authorPostsFeedUseCase;

    @Override
    public List<Post> getFriendsPosts(User user) {
        List<Author> authors = friendAuthorsUseCase.getAuthors(user.userId());
        return authors.stream()
                .flatMap(author -> authorPostsFeedUseCase.getByAuthor(author).stream())
                .sorted(Comparator.reverseOrder())
                .limit(1000)
                .toList();
    }
}
