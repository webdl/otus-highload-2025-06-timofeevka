package ru.webdl.otus.socialnetwork.infra.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "messages")
public class MongoMessage {
    @Id
    private UUID messageId;
    @Indexed
    private final UUID chatId;
    private final UUID senderId;
    private final String text;
}
