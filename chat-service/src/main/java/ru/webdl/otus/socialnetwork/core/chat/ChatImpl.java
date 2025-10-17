package ru.webdl.otus.socialnetwork.core.chat;

import jakarta.annotation.Nullable;
import lombok.*;
import ru.webdl.otus.socialnetwork.core.message.Message;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class ChatImpl implements Chat {
    private final UUID chatId;
    private final UUID firstMemberId;
    private final UUID secondMemberId;
    private UUID lastMessageId;
    private UUID lastMessageSenderId;
    private String lastMessageText;
    private LocalDateTime lastMessageCreatedAt;

    @Override
    public boolean isLastMessage(@NonNull Message message) {
        return this.lastMessageId.equals(message.getMessageId());
    }

    @Override
    public boolean changeLastMessage(@Nullable Message message) {
        UUID newMessageId = message == null ? null : message.getMessageId();
        if (Objects.equals(this.lastMessageId, newMessageId)) {
            return false;
        }
        this.lastMessageId = newMessageId;
        this.lastMessageSenderId = message == null ? null : message.getSenderId();
        this.lastMessageText = message == null ? null : truncateText(message.getText());
        this.lastMessageCreatedAt = message == null ? null : message.getCreatedAt();
        return true;
    }

    private String truncateText(String text) {
        return Optional.ofNullable(text)
                .map(t -> t.length() > 20 ? t.substring(0, 20) : t)
                .orElse(null);
    }

    static Chat create(@NonNull UUID firstMemberId, @NonNull UUID secondMemberId) {
        return builder()
                .chatId(UUID.randomUUID())
                .firstMemberId(firstMemberId)
                .secondMemberId(secondMemberId)
                .build();
    }

    public static class ChatImplBuilder implements Chat.ChatBuilder {
        public Chat build() {
            Objects.requireNonNull(chatId);
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
