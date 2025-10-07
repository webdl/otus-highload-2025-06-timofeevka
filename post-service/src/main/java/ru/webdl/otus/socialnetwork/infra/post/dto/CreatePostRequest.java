package ru.webdl.otus.socialnetwork.infra.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostRequest {
    private String content;
}
