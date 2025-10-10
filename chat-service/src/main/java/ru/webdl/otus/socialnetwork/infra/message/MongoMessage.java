package ru.webdl.otus.socialnetwork.infra.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "messages")
public class MongoMessage {
    @Id
    private UUID messageId;
    @Indexed
    private UUID chatId;
    private UUID senderId;
    private String text;
}
