package ru.webdl.otus.socialnetwork.infra.member;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringMongoMemberRepository extends MongoRepository<MongoMember, String> {
    Optional<MongoMember> findByUserId(UUID userId);

    List<MongoMember> findByUserIdIn(List<UUID> userIds);
}
