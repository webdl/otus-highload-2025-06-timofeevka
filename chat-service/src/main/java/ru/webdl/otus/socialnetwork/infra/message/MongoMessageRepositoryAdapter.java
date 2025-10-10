package ru.webdl.otus.socialnetwork.infra.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.message.Message;
import ru.webdl.otus.socialnetwork.core.message.MessageImpl;
import ru.webdl.otus.socialnetwork.core.message.MessageRepository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MongoMessageRepositoryAdapter implements MessageRepository {
    private final SpringMongoMessageRepository springRepository;

    @Override
    public List<Message> findByChatId(UUID chatId) {
        return springRepository.findByChatId(chatId).stream()
                .map(this::toDomainEntity)
                .toList();
    }

    @Override
    public Message save(Message message) {
        MongoMessage mongoEntity = toMongoEntity(message);
        MongoMessage saved = springRepository.save(mongoEntity);
        return toDomainEntity(saved);
    }

    private MongoMessage toMongoEntity(Message m) {
        return new MongoMessage(null, m.getChatId(), m.getSenderId(), m.getText());
    }

    private Message toDomainEntity(MongoMessage m) {
        return new MessageImpl(m.getMessageId(), m.getChatId(), m.getSenderId(), m.getText());
    }
}
