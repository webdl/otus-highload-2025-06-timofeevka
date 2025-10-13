package ru.webdl.otus.socialnetwork.core.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.chat.ChatEditUseCase;
import ru.webdl.otus.socialnetwork.core.member.Member;

@Service
@RequiredArgsConstructor
public class MessageDeleteUseCaseImpl implements MessageDeleteUseCase {
    private final MessageRepository repository;
    private final ChatEditUseCase chatEditUseCase;

    @Override
    @Transactional
    public void delete(Member member, Chat chat, Message message) {
        repository.delete(message);
        chatEditUseCase.replaceLastMessageAfterDeletion(chat, message);
    }
}
