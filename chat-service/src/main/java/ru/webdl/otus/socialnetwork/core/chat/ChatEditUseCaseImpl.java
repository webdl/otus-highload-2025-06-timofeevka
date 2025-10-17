package ru.webdl.otus.socialnetwork.core.chat;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.message.GetMessagesUseCase;
import ru.webdl.otus.socialnetwork.core.message.Message;

@Service
@RequiredArgsConstructor
public class ChatEditUseCaseImpl implements ChatEditUseCase {
    private final GetMessagesUseCase getMessagesUseCase;
    private final ChatRepository repository;

    @Override
    public void updateLastMessage(@NonNull Chat chat, @Nullable Message message) {
        boolean changed = chat.changeLastMessage(message);
        if (changed) {
            repository.updateLastMessage(chat);
        }
    }

    @Override
    public void replaceLastMessageAfterDeletion(@NonNull Chat chat, @NonNull Message deletedMessage) {
        if (chat.isLastMessage(deletedMessage)) {
            Message lastMessage = getMessagesUseCase.findLastMessage(chat).orElse(null);
            updateLastMessage(chat, lastMessage);
        }
    }
}
