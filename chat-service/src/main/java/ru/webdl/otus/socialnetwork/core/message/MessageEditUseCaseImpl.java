package ru.webdl.otus.socialnetwork.core.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.member.Member;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class MessageEditUseCaseImpl implements MessageEditUseCase {
    private final MessageRepository repository;

    @Override
    public Message edit(Member member, Chat chat, Message message, String text) {
        if (!message.getText().equals(text)) {
            MessageImpl edited = (MessageImpl) message;
            edited.setText(text);
            edited.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
            return repository.save(edited);
        }
        return message;
    }
}
