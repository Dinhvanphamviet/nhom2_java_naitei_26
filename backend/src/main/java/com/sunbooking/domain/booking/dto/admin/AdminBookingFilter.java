package com.sunbooking.domain.booking.dto.admin;

import com.sunbooking.domain.booking.entity.BookingStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminBookingFilter {
    private Long bookingId;
    private BookingStatus status;
    private Long tourId;
    private Long departureId;
    private LocalDate departureDate;
    private String searchKeyword;
}
