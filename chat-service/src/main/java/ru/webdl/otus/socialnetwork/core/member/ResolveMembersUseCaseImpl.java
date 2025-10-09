package ru.webdl.otus.socialnetwork.core.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.User;

@Service
@RequiredArgsConstructor
public class ResolveMembersUseCaseImpl implements ResolveMembersUseCase {
    private final MemberRepository memberRepository;

    @Override
    public Member getOrCreate(User user) {
        return memberRepository.findById(user.userId())
                .orElseGet(() -> createMember(user));
    }

    private Member createMember(User user) {
        String displayName = user.firstName() + " " + user.lastName();
        Member member = new MemberImpl(user.userId(), displayName);
        return memberRepository.save(member);
    }
}
