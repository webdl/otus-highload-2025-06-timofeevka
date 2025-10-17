package ru.webdl.otus.socialnetwork.core.message;

import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class MessageImpl implements Message {
    private final UUID messageId;
    private final UUID chatId;
    private final UUID senderId;
    private String text;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public boolean change(@NonNull String text) {
        if (this.text.equals(text)) {
            return false;
        }
        this.text = text;
        this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
        return true;
    }

    static Message create(@NonNull UUID chatId, @NonNull UUID senderId, @NonNull String text) {
        return builder()
                .messageId(UUID.randomUUID())
                .chatId(chatId)
                .senderId(senderId)
                .text(text)
                .createdAt(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }

    public static class MessageImplBuilder implements Message.MessageBuilder {
        public Message build() {
            Objects.requireNonNull(messageId);
            Objects.requireNonNull(chatId);
            Objects.requireNonNull(senderId);
            Objects.requireNonNull(text);
            Objects.requireNonNull(createdAt);
            return new MessageImpl(messageId, chatId, senderId, text, createdAt, updatedAt);
        }
    }
}
