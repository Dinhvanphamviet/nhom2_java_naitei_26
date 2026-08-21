package com.sunbooking.dto.content;

import java.time.LocalDateTime;

import com.sunbooking.entity.News;

public record NewsResponse(
        Long id,
        Long authorId,
        String title,
        String summary,
        String content,
        String thumbnailUrl,
        String status,
        LocalDateTime publishedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static NewsResponse from(News news) {
        return new NewsResponse(
                news.getId(),
                news.getAuthorId(),
                news.getTitle(),
                news.getSummary(),
                news.getContent(),
                news.getThumbnailUrl(),
                news.getStatus(),
                news.getPublishedAt(),
                news.getCreatedAt(),
                news.getUpdatedAt()
        );
    }
}