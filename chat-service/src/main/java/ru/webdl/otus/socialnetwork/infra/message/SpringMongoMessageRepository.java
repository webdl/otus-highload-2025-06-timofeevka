package ru.webdl.otus.socialnetwork.infra.message;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringMongoMessageRepository extends MongoRepository<MongoMessage, String> {
    List<MongoMessage> findByChatId(UUID chatId);
}
