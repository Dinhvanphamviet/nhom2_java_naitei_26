package com.sunbooking.domain.booking.dto.admin;

import com.sunbooking.domain.booking.entity.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminBookingStatusUpdateRequest {
    @NotNull(message = "Status cannot be null")
    private BookingStatus status;
}
