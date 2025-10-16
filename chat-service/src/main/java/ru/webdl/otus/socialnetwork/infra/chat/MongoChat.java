package ru.webdl.otus.socialnetwork.infra.chat;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "chats")
@CompoundIndex(name = "members_unique_idx",
        def = """
                {
                    "minMemberId": 1,
                    "maxMemberId": 1
                }""",
        unique = true)
public class MongoChat {
    @Id
    @Field(targetType = FieldType.STRING)
    @Setter(AccessLevel.PACKAGE)
    private UUID chatId;

    @Field(targetType = FieldType.STRING)
    private UUID firstMemberId;

    @Field(targetType = FieldType.STRING)
    private UUID secondMemberId;

    @Field(targetType = FieldType.STRING)
    private UUID lastMessageId;

    @Field(targetType = FieldType.STRING)
    private UUID lastMessageSenderId;
    private String lastMessageText;
    private LocalDateTime lastMessageCreatedAt;

    // Атрибуты для отслеживания уникальности по полям firstMemberId и secondMemberId
    @Field(targetType = FieldType.STRING)
    private UUID minMemberId;
    @Field(targetType = FieldType.STRING)
    private UUID maxMemberId;

    /**
     * Функция для вычисления полей minMemberId и maxMemberId. Используется для хранения уникальных комбинаций firstMemberId и
     * secondMemberId в CompoundIndex выше. Вызывается при создании нового объекта.
     */
    void calculateSortedIds() {
        if (firstMemberId.compareTo(secondMemberId) < 0) {
            this.minMemberId = firstMemberId;
            this.maxMemberId = secondMemberId;
        } else {
            this.minMemberId = secondMemberId;
            this.maxMemberId = firstMemberId;
        }
    }
}
