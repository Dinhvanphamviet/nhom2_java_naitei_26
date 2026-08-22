package com.sunbooking.domain.content.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewsRequest(
        Long authorId,
        @NotBlank @Size(max = 255) String title,
        String summary,
        @NotBlank String content,
        @Size(max = 255) String thumbnailUrl,
        @NotBlank @Size(max = 50) String status,
        LocalDateTime publishedAt
) {
}
