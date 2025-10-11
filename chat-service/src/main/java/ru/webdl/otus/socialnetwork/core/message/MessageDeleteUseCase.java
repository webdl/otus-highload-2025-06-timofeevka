package ru.webdl.otus.socialnetwork.core.message;

import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.member.Member;

public interface MessageDeleteUseCase {
    void delete(Member member, Chat chat, Message message);
}
