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
    private final MessageRepository messageRepository;

    @Override
    public Optional<Message> findById(UUID messageId) {
        return messageRepository.findByMessageId(messageId);
    }

    @Override
    public List<Message> findByChat(Member user, Chat chat) {
        return messageRepository.findByChatId(chat.getChatId());
    }
}
