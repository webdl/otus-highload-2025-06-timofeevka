package ru.webdl.otus.socialnetwork.core.chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.webdl.otus.socialnetwork.core.member.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetChatsUseCaseImpl implements GetChatsUseCase {
    private final ChatRepository repository;

    @Override
    public Optional<Chat> findById(@NonNull UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Chat> findByMember(@NonNull Member member) {
        return repository.findByMember(member);
    }
}
