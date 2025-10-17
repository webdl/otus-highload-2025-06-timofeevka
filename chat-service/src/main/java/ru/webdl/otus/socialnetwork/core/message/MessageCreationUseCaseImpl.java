package ru.webdl.otus.socialnetwork.core.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.chat.ChatEditUseCase;
import ru.webdl.otus.socialnetwork.core.member.Member;

@Service
@RequiredArgsConstructor
public class MessageCreationUseCaseImpl implements MessageCreationUseCase {
    private final ChatEditUseCase chatEditUseCase;
    private final MessageRepository repository;

    @Override
    @Transactional
    public Message create(Chat chat, Member sender, String text) {
        Message message = MessageImpl.create(chat.getChatId(), sender.userId(), text);
        Message saved = repository.save(message);
        chatEditUseCase.updateLastMessage(chat, saved);
        return saved;
    }
}
