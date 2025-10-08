package ru.webdl.otus.socialnetwork.core.member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    Optional<Member> findById(UUID userId);

    List<Member> getMembers(List<UUID> userIds);

    Member create(Member member);

    void save(Member member);
}
