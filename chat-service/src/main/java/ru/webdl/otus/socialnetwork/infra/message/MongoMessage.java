package ru.webdl.otus.socialnetwork.infra.message;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "messages")
public class MongoMessage {
    @Id
    @Field(targetType = FieldType.STRING)
    private final UUID messageId;

    @Field(targetType = FieldType.STRING)
    private final UUID chatId;

    @Setter(AccessLevel.PACKAGE)
    private int bucketId;

    @HashIndexed
    @Setter(AccessLevel.PACKAGE)
    private String compositeHash;

    @Field(targetType = FieldType.STRING)
    private final UUID senderId;

    private final String text;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;
}
