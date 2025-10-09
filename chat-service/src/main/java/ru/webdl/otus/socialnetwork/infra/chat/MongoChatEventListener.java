package ru.webdl.otus.socialnetwork.infra.chat;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class MongoChatEventListener extends AbstractMongoEventListener<MongoChat> {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<MongoChat> event) {
        MongoChat source = event.getSource();
        if (source.getChatId() == null) {
            source.calculateSortedIds();
        }
        super.onBeforeConvert(event);
    }
}
