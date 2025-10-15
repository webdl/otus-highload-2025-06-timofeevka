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
    @Setter(AccessLevel.PACKAGE)
    private UUID messageId;
    @Field(targetType = FieldType.STRING)
    private UUID chatId;
    @Setter(AccessLevel.PACKAGE)
    private int bucketId;
    @HashIndexed
    @Setter(AccessLevel.PACKAGE)
    private String compositeHash;
    @Field(targetType = FieldType.STRING)
    private UUID senderId;
    private String text;
    @Setter(AccessLevel.PACKAGE)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
