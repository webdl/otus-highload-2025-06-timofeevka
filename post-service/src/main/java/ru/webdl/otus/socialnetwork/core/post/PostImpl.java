package ru.webdl.otus.socialnetwork.core.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PostImpl implements Post {
    private final UUID postId;
    private final UUID userId;
    @Setter
    @NonNull
    private String content;
    private final OffsetDateTime created;

    public PostImpl(UUID userId, String content) {
        this(null, userId, content, null);
    }

    @Override
    public int compareTo(Post o) {
        return this.created.compareTo(o.getCreated());
    }
}
