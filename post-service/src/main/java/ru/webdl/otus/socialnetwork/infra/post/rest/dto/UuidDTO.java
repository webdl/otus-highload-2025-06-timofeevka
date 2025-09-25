package ru.webdl.otus.socialnetwork.infra.post.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UuidDTO {
    private UUID id;
}
