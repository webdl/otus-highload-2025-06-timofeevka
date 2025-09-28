package ru.webdl.otus.socialnetwork.core.author.cases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.author.entities.Author;
import ru.webdl.otus.socialnetwork.core.author.repositories.AuthorRepository;

@Service
@RequiredArgsConstructor
public class IncrementTotalPostsUseCaseImpl implements IncrementTotalPostsUseCase {
    private final AuthorRepository authorRepository;

    @Override
    public void incrementTotalPosts(Author author) {
        author.incrementTotalPosts();
        authorRepository.save(author);
    }

}
