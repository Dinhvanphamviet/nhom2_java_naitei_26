package com.sunbooking.domain.content.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FoodRequest(
        Long placeId,
        @NotBlank @Size(max = 255) String name,
        String description,
        @DecimalMin(value = "0.0", inclusive = true) BigDecimal price,
        @Size(max = 255) String imageUrl,
        @NotBlank @Size(max = 50) String status
) {
}
