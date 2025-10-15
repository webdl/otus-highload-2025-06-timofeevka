package ru.webdl.otus.socialnetwork.infra.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@Document(collection = "members")
public class MongoMember {
    @Id
    @Field(targetType = FieldType.STRING)
    private final UUID userId;
    private final String displayName;
}
