package ru.webdl.otus.socialnetwork.core.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncrementTotalPostsUseCaseImpl implements IncrementTotalPostsUseCase {
    private final AuthorRepository authorRepository;

    @Override
    public void incrementTotalPosts(Author author) {
        var authorImpl = (AuthorImpl) author;
        authorImpl.incrementTotalPosts();
        authorRepository.save(author);
    }
}
