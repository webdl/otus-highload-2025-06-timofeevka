package ru.webdl.otus.socialnetwork.core.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.chat.ChatEditUseCase;
import ru.webdl.otus.socialnetwork.core.member.Member;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class MessageEditUseCaseImpl implements MessageEditUseCase {
    private final MessageRepository repository;
    private final ChatEditUseCase chatEditUseCase;

    @Override
    @Transactional
    public Message edit(Member member, Chat chat, Message message, String text) {
        if (!message.getText().equals(text)) {
            MessageImpl edited = (MessageImpl) message;
            edited.setText(text);
            edited.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
            Message saved = repository.save(edited);
            chatEditUseCase.updateLastMessage(chat, saved);
        }
        return message;
    }
}
