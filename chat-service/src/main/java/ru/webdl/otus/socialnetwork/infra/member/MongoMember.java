package ru.webdl.otus.socialnetwork.infra.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@Document(collection = "members")
public class MongoMember {
    @Id
    private final UUID userId;
    private final String displayName;
}
