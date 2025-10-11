package ru.webdl.otus.socialnetwork.core.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class MessageImpl implements Message {
    private UUID messageId;
    private final UUID chatId;
    private final UUID senderId;
    private final String text;
    private LocalDateTime createdAt;
}
