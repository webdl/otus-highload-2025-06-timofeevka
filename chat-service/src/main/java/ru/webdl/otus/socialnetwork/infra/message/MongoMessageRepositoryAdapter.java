package ru.webdl.otus.socialnetwork.infra.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.webdl.otus.socialnetwork.core.message.Message;
import ru.webdl.otus.socialnetwork.core.message.MessageImpl;
import ru.webdl.otus.socialnetwork.core.message.MessageRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MongoMessageRepositoryAdapter implements MessageRepository {
    private final SpringMongoMessageRepository springRepository;

    @Override
    public Optional<Message> findByMessageId(UUID id) {
        return springRepository.findByMessageId(id)
                .map(this::toDomainEntity);
    }

    @Override
    public List<Message> findByChatId(UUID chatId) {
        return springRepository.findByChatId(chatId).stream()
                .map(this::toDomainEntity)
                .toList();
    }

    @Override
    public Optional<Message> findLastMessage(UUID chatId) {
        return springRepository.findFirstByOrderByCreatedAtDesc(chatId)
                .map(this::toDomainEntity);
    }

    @Override
    public Message save(Message message) {
        MongoMessage mongoEntity = toMongoEntity(message);
        MongoMessage saved = springRepository.save(mongoEntity);
        return toDomainEntity(saved);
    }

    @Override
    public void delete(Message message) {
        MongoMessage mongoMessage = toMongoEntity(message);
        springRepository.delete(mongoMessage);
    }

    private MongoMessage toMongoEntity(Message m) {
        return new MongoMessage(m.getMessageId(), m.getChatId(), m.getSenderId(), m.getText(), m.getCreatedAt(), m.getUpdatedAt());
    }

    private Message toDomainEntity(MongoMessage m) {
        return MessageImpl.builder()
                .messageId(m.getMessageId())
                .chatId(m.getChatId())
                .senderId(m.getSenderId())
                .text(m.getText())
                .createdAt(m.getCreatedAt())
                .updatedAt(m.getUpdatedAt())
                .build();
    }
}
