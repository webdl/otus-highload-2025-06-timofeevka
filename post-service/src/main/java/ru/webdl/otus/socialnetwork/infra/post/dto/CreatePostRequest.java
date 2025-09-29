package ru.webdl.otus.socialnetwork.infra.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreatePostRequest {
    private UUID authorId;
    private String content;
}
