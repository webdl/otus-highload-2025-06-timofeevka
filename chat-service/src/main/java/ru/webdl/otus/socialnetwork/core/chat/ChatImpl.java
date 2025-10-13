package ru.webdl.otus.socialnetwork.core.chat;

import lombok.*;
import ru.webdl.otus.socialnetwork.core.message.Message;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatImpl implements Chat {
    private UUID chatId;
    private final UUID firstMemberId;
    private final UUID secondMemberId;
    @Setter(AccessLevel.PACKAGE)
    private UUID lastMessageId;
    @Setter(AccessLevel.PACKAGE)
    private UUID lastMessageSenderId;
    @Setter(AccessLevel.PACKAGE)
    private String lastMessageText;
    @Setter(AccessLevel.PACKAGE)
    private LocalDateTime lastMessageCreatedAt;

    @Override
    public boolean isLastMessage(@NonNull Message message) {
        return this.lastMessageId.equals(message.getMessageId());
    }

    public static ChatImplBuilder create(@NonNull UUID firstMemberId, @NonNull UUID secondMemberId) {
        return builder()
                .firstMemberId(firstMemberId)
                .secondMemberId(secondMemberId);
    }

    public static class ChatImplBuilder {
        public ChatImpl build() {
            Objects.requireNonNull(firstMemberId);
            Objects.requireNonNull(secondMemberId);
            return new ChatImpl(chatId,
                    firstMemberId,
                    secondMemberId,
                    lastMessageId,
                    lastMessageSenderId,
                    lastMessageText,
                    lastMessageCreatedAt);
        }
    }
}
