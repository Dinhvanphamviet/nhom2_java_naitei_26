package com.sunbooking.domain.content.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sunbooking.domain.content.entity.Food;

public record FoodResponse(
        Long id,
        Long placeId,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static FoodResponse from(Food food) {
        return new FoodResponse(
                food.getId(),
                food.getPlaceId(),
                food.getName(),
                food.getDescription(),
                food.getPrice(),
                food.getImageUrl(),
                food.getStatus(),
                food.getCreatedAt(),
                food.getUpdatedAt()
        );
    }
}