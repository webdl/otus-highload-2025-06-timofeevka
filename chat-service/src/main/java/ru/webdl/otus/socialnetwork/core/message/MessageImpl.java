package ru.webdl.otus.socialnetwork.core.message;

import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageImpl implements Message {
    private final UUID messageId;
    private final UUID chatId;
    private final UUID senderId;
    @Setter(AccessLevel.PACKAGE)
    private String text;
    private final LocalDateTime createdAt;
    @Setter(AccessLevel.PACKAGE)
    private LocalDateTime updatedAt;

    public static MessageImplBuilder create(@NonNull UUID chatId, @NonNull UUID senderId, @NonNull String text) {
        return builder()
                .chatId(chatId)
                .senderId(senderId)
                .text(text);
    }

    public static class MessageImplBuilder {
        public MessageImpl build() {
            Objects.requireNonNull(chatId);
            Objects.requireNonNull(senderId);
            Objects.requireNonNull(text);
            return new MessageImpl(messageId, chatId, senderId, text, createdAt, updatedAt);
        }
    }
}
