package ru.webdl.otus.socialnetwork.core.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.member.Member;

@Service
@RequiredArgsConstructor
public class MessageCreationUseCaseImpl implements MessageCreationUseCase {
    private final MessageRepository messageRepository;

    @Override
    public Message create(Chat chat, Member sender, String text) {
        MessageImpl message = new MessageImpl(chat.getChatId(), sender.userId(), text);
        return messageRepository.save(message);
    }
}
