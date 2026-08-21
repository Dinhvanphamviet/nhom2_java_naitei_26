package com.sunbooking.dto.content;

import java.time.LocalDateTime;

import com.sunbooking.entity.Place;

public record PlaceResponse(
        Long id,
        String name,
        String description,
        String address,
        String city,
        String imageUrl,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PlaceResponse from(Place place) {
        return new PlaceResponse(
                place.getId(),
                place.getName(),
                place.getDescription(),
                place.getAddress(),
                place.getCity(),
                place.getImageUrl(),
                place.getStatus(),
                place.getCreatedAt(),
                place.getUpdatedAt()
        );
    }
}