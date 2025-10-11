package ru.webdl.otus.socialnetwork.core.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.core.user.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResolveMembersUseCaseImpl implements ResolveMembersUseCase {
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    @Override
    public Member getOrCreate(UUID userId) {
        return memberRepository.findById(userId)
                .orElseGet(() -> createMember(userRepository.getBy(userId)));
    }

    private Member createMember(User user) {
        String displayName = user.firstName() + " " + user.lastName();
        Member member = new MemberImpl(user.userId(), displayName);
        return memberRepository.save(member);
    }
}
