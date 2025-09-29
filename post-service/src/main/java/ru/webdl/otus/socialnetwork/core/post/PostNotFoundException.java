package ru.webdl.otus.socialnetwork.core.post;

import java.util.UUID;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(UUID postId) {
        super("Post with id " + postId + " not found");
    }
}
