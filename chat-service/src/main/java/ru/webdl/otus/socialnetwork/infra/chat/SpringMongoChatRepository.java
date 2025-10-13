package ru.webdl.otus.socialnetwork.infra.chat;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    @Query("{ '_id' : ?0 }")
    @Update("""
                {
                    $set: {
                        'lastMessageId': ?1,
                        'lastMessageSenderId': ?2,
                        'lastMessageText': ?3,
                        'lastMessageCreatedAt': ?4
                    }
                }
            """)
    void updateLastMessageFields(UUID chatId, UUID messageId, UUID senderId, String text, LocalDateTime createdAt);
}
