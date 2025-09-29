package ru.webdl.otus.socialnetwork.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.author.Author;
import ru.webdl.otus.socialnetwork.core.author.CreateAuthorUseCase;
import ru.webdl.otus.socialnetwork.core.post.CreatePostUseCase;
import ru.webdl.otus.socialnetwork.core.post.Post;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreatePostServiceImpl implements CreatePostService {
    private final CreateAuthorUseCase createAuthorUseCase;
    private final CreatePostUseCase createPostUseCase;

    @Override
    public Post create(UUID authorId, String content) {
        Author author = createAuthorUseCase.createIfNotExists(authorId);
        return createPostUseCase.create(author, content);
    }
}
