package com.sunbooking.domain.booking.service;

import com.sunbooking.domain.booking.dto.BookingRequest;
import com.sunbooking.domain.booking.dto.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(Long userId, BookingRequest request);
    List<BookingResponse> getUserBookingHistory(Long userId);
    BookingResponse cancelBooking(Long userId, Long bookingId);
}
