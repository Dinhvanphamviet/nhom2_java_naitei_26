package com.sunbooking.domain.review.dto;

import com.sunbooking.domain.review.entity.ReviewImage;

import java.time.LocalDateTime;

public record ReviewImageResponse(
        Long id,
        String imageUrl,
        LocalDateTime createdAt
) {
    public static ReviewImageResponse from(ReviewImage image) {
        return new ReviewImageResponse(
                image.getId(),
                image.getImageUrl(),
                image.getCreatedAt()
        );
    }
}
