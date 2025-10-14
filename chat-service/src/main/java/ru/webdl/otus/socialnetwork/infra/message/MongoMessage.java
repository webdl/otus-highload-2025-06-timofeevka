package ru.webdl.otus.socialnetwork.infra.message;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "messages")
public class MongoMessage {
    @Id
    @Setter(AccessLevel.PACKAGE)
    private UUID messageId;
    private UUID chatId;
    @Setter(AccessLevel.PACKAGE)
    private int bucketId;
    @HashIndexed
    @Setter(AccessLevel.PACKAGE)
    private String compositeHash;
    private UUID senderId;
    private String text;
    @Setter(AccessLevel.PACKAGE)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
