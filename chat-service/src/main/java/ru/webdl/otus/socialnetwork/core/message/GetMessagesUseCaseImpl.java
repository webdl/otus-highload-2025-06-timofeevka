package ru.webdl.otus.socialnetwork.core.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.member.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetMessagesUseCaseImpl implements GetMessagesUseCase {
    private final MessageRepository repository;

    @Override
    public Optional<Message> findById(UUID messageId) {
        return repository.findByMessageId(messageId);
    }

    @Override
    public List<Message> findByChat(Member user, Chat chat) {
        return repository.findByChatId(chat.getChatId());
    }

    @Override
    public Optional<Message> findLastMessage(Chat chat) {
        return repository.findLastMessage(chat.getLastMessageId());
    }
}
