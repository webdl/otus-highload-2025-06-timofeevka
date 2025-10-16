package ru.webdl.otus.socialnetwork.core.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.chat.ChatEditUseCase;
import ru.webdl.otus.socialnetwork.core.member.Member;

@Service
@RequiredArgsConstructor
public class MessageEditUseCaseImpl implements MessageEditUseCase {
    private final MessageRepository repository;
    private final ChatEditUseCase chatEditUseCase;

    @Override
    @Transactional
    public Message edit(Member member, Chat chat, Message message, String text) {
        boolean changed = message.change(text);
        if (changed) {
            Message saved = repository.save(message);
            chatEditUseCase.updateLastMessage(chat, saved);
        }
        return message;
    }
}
