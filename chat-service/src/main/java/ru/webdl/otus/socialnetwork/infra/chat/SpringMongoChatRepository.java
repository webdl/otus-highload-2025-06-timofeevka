package ru.webdl.otus.socialnetwork.infra.chat;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringMongoChatRepository extends MongoRepository<MongoChat, String> {
    Optional<MongoChat> findByChatId(UUID chatId);

    @Query("""
            {
                $or: [
                    { 'firstMemberId': ?0, 'secondMemberId': ?1 },
                    { 'firstMemberId': ?1, 'secondMemberId': ?0 }
                ]
            }
            """)
    Optional<MongoChat> findByMemberIds(UUID firstMemberId, UUID secondMemberId);

    List<MongoChat> findByFirstMemberIdOrSecondMemberId(UUID firstMemberId, UUID secondMemberId);
}
