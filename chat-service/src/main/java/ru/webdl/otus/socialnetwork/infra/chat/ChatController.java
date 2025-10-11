package ru.webdl.otus.socialnetwork.infra.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.chat.ChatCreationUseCase;
import ru.webdl.otus.socialnetwork.core.chat.ChatNotFoundException;
import ru.webdl.otus.socialnetwork.core.chat.GetChatsUseCase;
import ru.webdl.otus.socialnetwork.core.member.Member;
import ru.webdl.otus.socialnetwork.core.member.ResolveMembersUseCase;
import ru.webdl.otus.socialnetwork.infra.chat.dto.ChatCreationRequest;
import ru.webdl.otus.socialnetwork.infra.chat.dto.ChatResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
public class ChatController {
    private final ResolveMembersUseCase resolveMembersUseCase;
    private final ChatCreationUseCase chatCreationUseCase;
    private final GetChatsUseCase getChatsUseCase;

    @PostMapping
    public ResponseEntity<ChatResponse> create(@RequestHeader("userId") UUID userId,
                                               @RequestBody ChatCreationRequest data) {
        Member firstMember = resolveMembersUseCase.getOrCreate(userId);
        Member secondMember = resolveMembersUseCase.getOrCreate(data.secondMemberId());
        Chat chat = chatCreationUseCase.create(firstMember, secondMember);
        return ResponseEntity.ok(new ChatResponse(chat));
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getMyChats(@RequestHeader("userId") UUID userId) {
        Member member = resolveMembersUseCase.getOrCreate(userId);
        return ResponseEntity.ok(getChatsUseCase.findByMember(member).stream()
                .map(ChatResponse::new)
                .toList());
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatResponse> getChat(@PathVariable("chatId") UUID chatId) {
        Chat chat = getChatsUseCase.findById(chatId).orElseThrow(() -> new ChatNotFoundException(chatId));
        return ResponseEntity.ok(new ChatResponse(chat));
    }
}
