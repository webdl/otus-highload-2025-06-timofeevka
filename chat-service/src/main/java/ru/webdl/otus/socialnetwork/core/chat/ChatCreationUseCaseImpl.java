package ru.webdl.otus.socialnetwork.core.chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.member.Member;

@Service
@RequiredArgsConstructor
public class ChatCreationUseCaseImpl implements ChatCreationUseCase {
    private final ChatRepository chatRepository;

    @Override
    public Chat create(@NonNull Member first, @NonNull Member second) {
        if (chatRepository.findByMembers(first, second).isPresent()) {
            throw new ChatAlreadyExistsException(first, second);
        }
        Chat chat = ChatImpl.create(first.userId(), second.userId()).build();
        return chatRepository.save(chat);
    }
}
