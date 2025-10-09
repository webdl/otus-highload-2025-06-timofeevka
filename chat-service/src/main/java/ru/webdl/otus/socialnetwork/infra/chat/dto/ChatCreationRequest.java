package ru.webdl.otus.socialnetwork.infra.chat.dto;

import java.util.UUID;

public record ChatCreationRequest(UUID secondMemberId) {
}
