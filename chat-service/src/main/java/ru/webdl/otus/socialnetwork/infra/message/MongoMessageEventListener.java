package ru.webdl.otus.socialnetwork.infra.message;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class MongoMessageEventListener extends AbstractMongoEventListener<MongoMessage> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHH");

    @Override
    public void onBeforeConvert(BeforeConvertEvent<MongoMessage> event) {
        MongoMessage source = event.getSource();
        if (source.getMessageId() == null) {
            UUID uuid = UUID.randomUUID();
            LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);
            int bucketId = calculateBucket(createdAt);
            String compositeHash = createCompositeHash(uuid, bucketId);

            source.setMessageId(uuid);
            source.setCreatedAt(createdAt);
            source.setBucketId(bucketId);
            source.setCompositeHash(compositeHash);
        }
        super.onBeforeConvert(event);
    }

    private int calculateBucket(LocalDateTime createdAt) {
        return Integer.parseInt(createdAt.format(formatter));
    }

    private String createCompositeHash(UUID chatId, int bucketId) {
        return chatId.toString() + "|" + bucketId;
    }
}
