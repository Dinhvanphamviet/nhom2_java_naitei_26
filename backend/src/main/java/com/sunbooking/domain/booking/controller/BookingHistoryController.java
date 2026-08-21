package com.sunbooking.domain.booking.controller;

import com.sunbooking.domain.booking.dto.BookingResponse;
import com.sunbooking.domain.booking.service.BookingService;
import com.sunbooking.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingHistoryController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookings(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        Long userId = userDetails.getUser().getId();
        
        List<BookingResponse> history = bookingService.getUserBookingHistory(userId);
        return ResponseEntity.ok(history);
    }
}
