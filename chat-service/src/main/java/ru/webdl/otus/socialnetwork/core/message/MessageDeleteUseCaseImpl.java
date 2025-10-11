package ru.webdl.otus.socialnetwork.core.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.member.Member;

@Service
@RequiredArgsConstructor
public class MessageDeleteUseCaseImpl implements MessageDeleteUseCase {
    private final MessageRepository messageRepository;

    @Override
    public void delete(Member member, Chat chat, Message message) {
        messageRepository.delete(message);
    }
}
