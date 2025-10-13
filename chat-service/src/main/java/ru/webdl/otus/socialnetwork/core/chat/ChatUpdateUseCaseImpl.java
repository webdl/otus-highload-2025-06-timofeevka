package ru.webdl.otus.socialnetwork.core.chat;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.message.GetMessagesUseCase;
import ru.webdl.otus.socialnetwork.core.message.Message;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatUpdateUseCaseImpl implements ChatUpdateUseCase {
    private final GetMessagesUseCase getMessagesUseCase;
    private final ChatRepository repository;

    @Override
    public void updateLastMessage(@NonNull Chat chat, @Nullable Message message) {
        ChatImpl chatObj = (ChatImpl) chat;
        Optional.ofNullable(message).ifPresentOrElse(msg -> {
                    chatObj.setLastMessageId(msg.getMessageId());
                    chatObj.setLastMessageSenderId(msg.getSenderId());
                    chatObj.setLastMessageText(truncateText(msg.getText()));
                    chatObj.setLastMessageCreatedAt(msg.getCreatedAt());
                }, () -> {
                    chatObj.setLastMessageId(null);
                    chatObj.setLastMessageSenderId(null);
                    chatObj.setLastMessageText(null);
                    chatObj.setLastMessageCreatedAt(null);
                }
        );
        repository.updateLastMessage(chatObj);
    }

    private String truncateText(String text) {
        return Optional.ofNullable(text)
                .map(t -> t.length() > 20 ? t.substring(0, 20) : t)
                .orElse(null);
    }

    @Override
    public void replaceLastMessageAfterDeletion(@NonNull Chat chat, @NonNull Message deletedMessage) {
        if (chat.isLastMessage(deletedMessage)) {
            Message lastMessage = getMessagesUseCase.findLastMessage(chat).orElse(null);
            updateLastMessage(chat, lastMessage);
        }
    }
}
