package ru.webdl.otus.socialnetwork.infra.chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.chat.ChatImpl;
import ru.webdl.otus.socialnetwork.core.chat.ChatRepository;
import ru.webdl.otus.socialnetwork.core.member.Member;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MongoChatRepositoryAdapter implements ChatRepository {
    private final SpringMongoChatRepository springRepository;

    @Override
    public Optional<Chat> findById(@NonNull UUID id) {
        return springRepository.findByChatId(id)
                .map(this::toDomainEntity);
    }

    @Override
    public Optional<Chat> findByMembers(@NonNull Member first, @NonNull Member second) {
        return springRepository.findByMemberIds(first.userId(), second.userId())
                .map(this::toDomainEntity);
    }

    @Override
    public Chat save(@NonNull Chat chat) {
        MongoChat mongoChat = toMongoEntity(chat);
        MongoChat saved = springRepository.save(mongoChat);
        return toDomainEntity(saved);
    }

    private MongoChat toMongoEntity(Chat c) {
        return new MongoChat(c.getChatId(),
                c.getFirstMemberId(),
                c.getSecondMemberId(),
                c.getLastMessageId(),
                c.getLastMessageSenderId(),
                c.getLastMessageText(),
                c.getMinMemberId(),
                c.getMaxMemberId());
    }

    private Chat toDomainEntity(MongoChat c) {
        return new ChatImpl(c.getChatId(),
                c.getFirstMemberId(),
                c.getSecondMemberId(),
                c.getLastMessageId(),
                c.getLastMessageSenderId(),
                c.getLastMessageText(),
                c.getMinMemberId(),
                c.getMaxMemberId());
    }
}
