package com.sunbooking.domain.content.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PlaceRequest(
        @NotBlank @Size(max = 255) String name,
        String description,
        String address,
        String city,
        @Size(max = 255) String imageUrl,
        @NotBlank @Size(max = 50) String status
) {
}
