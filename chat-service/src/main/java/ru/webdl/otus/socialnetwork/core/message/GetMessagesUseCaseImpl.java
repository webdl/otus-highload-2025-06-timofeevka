package ru.webdl.otus.socialnetwork.core.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.member.Member;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMessagesUseCaseImpl implements GetMessagesUseCase {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> getAll(Member user, Chat chat) {
        return messageRepository.findByChatId(chat.getChatId());
    }
}
