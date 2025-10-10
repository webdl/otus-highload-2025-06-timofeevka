package ru.webdl.otus.socialnetwork.infra.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.chat.ChatNotFoundException;
import ru.webdl.otus.socialnetwork.core.chat.ChatRepository;
import ru.webdl.otus.socialnetwork.core.member.Member;
import ru.webdl.otus.socialnetwork.core.member.ResolveMembersUseCase;
import ru.webdl.otus.socialnetwork.core.message.GetMessagesUseCase;
import ru.webdl.otus.socialnetwork.core.message.Message;
import ru.webdl.otus.socialnetwork.core.message.MessageCreationUseCase;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.core.user.UserRepository;
import ru.webdl.otus.socialnetwork.infra.message.dto.MessageCreationRequest;
import ru.webdl.otus.socialnetwork.infra.message.dto.MessageResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats/{chatId}/messages")
public class MessageController {
    private final MessageCreationUseCase messageCreationUseCase;
    private final GetMessagesUseCase getMessagesUseCase;
    private final ResolveMembersUseCase resolveMembersUseCase;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestHeader("userId") UUID userId,
                                                  @PathVariable UUID chatId,
                                                  @RequestBody MessageCreationRequest data) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException(chatId));
        User user = userRepository.getBy(userId);
        Member sender = resolveMembersUseCase.getOrCreate(user);
        Message message = messageCreationUseCase.create(chat, sender, data.getText());
        return ResponseEntity.ok(new MessageResponse(message));
    }

    @GetMapping
    public ResponseEntity<List<MessageResponse>> getAll(@RequestHeader("userId") UUID userId,
                                                        @PathVariable UUID chatId) {
        User user = userRepository.getBy(userId);
        Member member = resolveMembersUseCase.getOrCreate(user);
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException(chatId));
        List<Message> messages = getMessagesUseCase.getAll(member, chat);
        return ResponseEntity.ok(messages.stream().map(MessageResponse::new).toList());
    }
}
