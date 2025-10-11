package ru.webdl.otus.socialnetwork.core.chat;

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
    public void setLastMessage(Chat chat, Message message) {
        ChatImpl chatObj = (ChatImpl) chat;
        chatObj.setLastMessage(message);
        repository.save(chatObj);
    }

    @Override
    public void replaceLastMessageAfterDeletion(Chat chat, Message deletedMessage) {
        ChatImpl chatObj = (ChatImpl) chat;
        if (chatObj.isLastMessage(deletedMessage)) {
            Optional<Message> lastMessage = getMessagesUseCase.findLastMessage(chatObj);
            lastMessage.ifPresent(message -> setLastMessage(chatObj, message));
            repository.save(chatObj);
        }
    }
}
