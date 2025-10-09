package ru.webdl.otus.socialnetwork.infra.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.webdl.otus.socialnetwork.core.member.Member;
import ru.webdl.otus.socialnetwork.core.member.MemberImpl;
import ru.webdl.otus.socialnetwork.core.member.MemberRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MongoMemberRepositoryAdapter implements MemberRepository {
    private final SpringMongoMemberRepository springRepository;

    @Override
    public Optional<Member> findById(UUID userId) {
        return springRepository.findByUserId(userId)
                .map(this::toDomainMember);
    }

    @Override
    public List<Member> getMembers(List<UUID> userIds) {
        return springRepository.findByUserIdIn(userIds).stream()
                .map(this::toDomainMember)
                .toList();
    }

    @Override
    public Member save(Member member) {
        MongoMember entityMember = toEntityMember(member);
        MongoMember saved = springRepository.save(entityMember);
        return toDomainMember(saved);
    }

    private Member toDomainMember(MongoMember m) {
        return new MemberImpl(m.getUserId(), m.getDisplayName());
    }

    private MongoMember toEntityMember(Member m) {
        return new MongoMember(m.userId(), m.displayName());
    }
}
