package ru.webdl.otus.socialnetwork.core.message;

import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.member.Member;

public interface MessageCreationUseCase {
    Message create(Chat chat, Member sender, String text);

    Message.MessageBuilder builder();
}
