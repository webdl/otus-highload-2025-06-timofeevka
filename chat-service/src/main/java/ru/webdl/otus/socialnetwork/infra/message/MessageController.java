package ru.webdl.otus.socialnetwork.infra.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.chat.ChatNotFoundException;
import ru.webdl.otus.socialnetwork.core.chat.GetChatsUseCase;
import ru.webdl.otus.socialnetwork.core.member.Member;
import ru.webdl.otus.socialnetwork.core.member.ResolveMembersUseCase;
import ru.webdl.otus.socialnetwork.core.message.*;
import ru.webdl.otus.socialnetwork.infra.message.dto.MessageCreationRequest;
import ru.webdl.otus.socialnetwork.infra.message.dto.MessageResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats/{chatId}/messages")
public class MessageController {
    private final GetMessagesUseCase getMessagesUseCase;
    private final MessageCreationUseCase messageCreationUseCase;
    private final MessageDeleteUseCase messageDeleteUseCase;
    private final GetChatsUseCase getChatsUseCase;
    private final ResolveMembersUseCase resolveMembersUseCase;

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestHeader("userId") UUID userId,
                                                  @PathVariable UUID chatId,
                                                  @RequestBody MessageCreationRequest data) {
        Member sender = resolveMembersUseCase.getOrCreate(userId);
        Chat chat = getChatsUseCase.findById(chatId).orElseThrow(() -> new ChatNotFoundException(chatId));
        Message message = messageCreationUseCase.create(chat, sender, data.getText());
        return ResponseEntity.ok(new MessageResponse(message));
    }

    @GetMapping
    public ResponseEntity<List<MessageResponse>> getAll(@RequestHeader("userId") UUID userId,
                                                        @PathVariable UUID chatId) {
        Member member = resolveMembersUseCase.getOrCreate(userId);
        Chat chat = getChatsUseCase.findById(chatId).orElseThrow(() -> new ChatNotFoundException(chatId));
        List<Message> messages = getMessagesUseCase.findByChat(member, chat);
        return ResponseEntity.ok(messages.stream().map(MessageResponse::new).toList());
    }

    @DeleteMapping("/{messageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestHeader("userId") UUID userId,
                       @PathVariable UUID chatId,
                       @PathVariable UUID messageId) {
        Member member = resolveMembersUseCase.getOrCreate(userId);
        Chat chat = getChatsUseCase.findById(chatId).orElseThrow(() -> new ChatNotFoundException(chatId));
        Message message = getMessagesUseCase.findById(messageId).orElseThrow(() -> new MessageNotFoundException(messageId));
        messageDeleteUseCase.delete(member, chat, message);
    }
}
