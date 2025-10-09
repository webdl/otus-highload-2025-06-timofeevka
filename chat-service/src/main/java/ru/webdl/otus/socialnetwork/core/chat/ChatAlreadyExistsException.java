package ru.webdl.otus.socialnetwork.core.chat;

import ru.webdl.otus.socialnetwork.core.member.Member;

public class ChatAlreadyExistsException extends RuntimeException {
    public ChatAlreadyExistsException(Member first, Member second) {
        super(String.format("Chat already exists for member %s and %s", first.displayName(), second.displayName()));
    }
}
