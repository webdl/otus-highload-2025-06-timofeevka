package ru.webdl.otus.socialnetwork.infra.message;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Component
public class MongoMessageEventListener extends AbstractMongoEventListener<MongoMessage> {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<MongoMessage> event) {
        MongoMessage source = event.getSource();
        if (source.getMessageId() == null) {
            source.setMessageId(UUID.randomUUID());
            source.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
        }
        super.onBeforeConvert(event);
    }
}
