package ru.webdl.otus.socialnetwork.infra.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdl.otus.socialnetwork.core.chat.Chat;
import ru.webdl.otus.socialnetwork.core.chat.ChatCreationUseCase;
import ru.webdl.otus.socialnetwork.core.member.Member;
import ru.webdl.otus.socialnetwork.core.member.ResolveMembersUseCase;
import ru.webdl.otus.socialnetwork.core.user.User;
import ru.webdl.otus.socialnetwork.core.user.UserRepository;
import ru.webdl.otus.socialnetwork.infra.chat.dto.ChatCreationRequest;
import ru.webdl.otus.socialnetwork.infra.chat.dto.ChatResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
public class ChatController {
    private final ResolveMembersUseCase resolveMembersUseCase;
    private final ChatCreationUseCase chatCreationUseCase;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ChatResponse> create(@RequestHeader("userId") UUID userId,
                                               @RequestBody ChatCreationRequest data) {
        User firstUser = userRepository.getBy(userId);
        User secondUser = userRepository.getBy(data.secondMemberId());
        Member firstMember = resolveMembersUseCase.getOrCreate(firstUser);
        Member secondMember = resolveMembersUseCase.getOrCreate(secondUser);
        Chat chat = chatCreationUseCase.create(firstMember, secondMember);
        return ResponseEntity.ok(new ChatResponse(chat));
    }
}
