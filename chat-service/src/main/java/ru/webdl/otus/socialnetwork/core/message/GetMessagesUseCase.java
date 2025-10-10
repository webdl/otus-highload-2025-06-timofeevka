package ru.webdl.otus.socialnetwork.core.message;

import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.member.Member;

import java.util.List;

public interface GetMessagesUseCase {
    List<Message> getAll(Member user, Chat chat);
}
