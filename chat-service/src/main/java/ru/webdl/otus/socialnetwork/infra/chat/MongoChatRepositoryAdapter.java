package ru.webdl.otus.socialnetwork.infra.chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.chat.ChatImpl;
import ru.webdl.otus.socialnetwork.core.chat.ChatRepository;
import ru.webdl.otus.socialnetwork.core.member.Member;

import java.util.List;
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
    public List<Chat> findByMember(@NonNull Member member) {
        return springRepository.findByFirstMemberIdOrSecondMemberId(member.userId(), member.userId()).stream()
                .map(this::toDomainEntity)
                .toList();
    }

    @Override
    public void updateLastMessage(@NonNull Chat c) {
        springRepository.updateLastMessageFields(c.getChatId(), c.getLastMessageId(), c.getLastMessageSenderId(), c.getLastMessageText(),
                c.getLastMessageCreatedAt());
    }

    @Override
    public Chat save(@NonNull Chat chat) {
        MongoChat mongoChat = toMongoEntity(chat);
        MongoChat saved = springRepository.save(mongoChat);
        return toDomainEntity(saved);
    }

    private MongoChat toMongoEntity(Chat c) {
        return MongoChat.builder()
                .chatId(c.getChatId())
                .firstMemberId(c.getFirstMemberId())
                .secondMemberId(c.getSecondMemberId())
                .lastMessageId(c.getLastMessageId())
                .lastMessageSenderId(c.getLastMessageSenderId())
                .lastMessageText(c.getLastMessageText())
                .lastMessageCreatedAt(c.getLastMessageCreatedAt())
                .build();
    }

    private Chat toDomainEntity(MongoChat c) {
        return ChatImpl.builder()
                .chatId(c.getChatId())
                .firstMemberId(c.getFirstMemberId())
                .secondMemberId(c.getSecondMemberId())
                .lastMessageId(c.getLastMessageId())
                .lastMessageSenderId(c.getLastMessageSenderId())
                .lastMessageText(c.getLastMessageText())
                .lastMessageCreatedAt(c.getLastMessageCreatedAt())
                .build();
    }
}
