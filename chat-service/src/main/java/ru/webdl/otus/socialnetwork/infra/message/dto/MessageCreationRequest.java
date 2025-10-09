package ru.webdl.otus.socialnetwork.infra.message.dto;

import lombok.Data;

@Data
public class MessageCreationRequest {
    private final String text;
}
