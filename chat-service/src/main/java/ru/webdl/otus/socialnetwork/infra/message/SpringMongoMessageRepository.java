package ru.webdl.otus.socialnetwork.infra.message;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringMongoMessageRepository extends MongoRepository<MongoMessage, String> {
    Optional<MongoMessage> findByMessageId(UUID id);

    List<MongoMessage> findByChatId(UUID chatId);
}
