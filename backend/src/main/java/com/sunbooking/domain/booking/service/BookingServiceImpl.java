package com.sunbooking.domain.booking.service;

import com.sunbooking.domain.booking.dto.BookingRequest;
import com.sunbooking.domain.booking.dto.BookingResponse;
import com.sunbooking.domain.booking.dto.BookingTravelerDto;
import com.sunbooking.domain.booking.entity.Booking;
import com.sunbooking.domain.booking.entity.BookingStatus;
import com.sunbooking.domain.booking.entity.BookingTraveler;
import com.sunbooking.domain.booking.repository.BookingRepository;
import com.sunbooking.domain.tour.entity.TourDeparture;
import com.sunbooking.domain.tour.repository.TourDepartureRepository;
import com.sunbooking.domain.user.entity.User;
import com.sunbooking.domain.user.repository.UserRepository;
import com.sunbooking.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TourDepartureRepository tourDepartureRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(Long userId, BookingRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TourDeparture departure = tourDepartureRepository.findByIdWithTour(request.getDepartureId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour departure not found"));

        int numberOfPeople = request.getTravelers().size();
        
        // Check slot
        if (departure.getAvailableSlot() < numberOfPeople) {
            throw new IllegalArgumentException("Not enough available slots for this departure");
        }

        // Temporarily reserve slots
        departure.setAvailableSlot(departure.getAvailableSlot() - numberOfPeople);
        tourDepartureRepository.save(departure);

        BigDecimal totalPrice = departure.getPrice().multiply(BigDecimal.valueOf(numberOfPeople));

        Booking booking = Booking.builder()
                .user(user)
                .departure(departure)
                .bookingDate(LocalDateTime.now())
                .numberOfPeople(numberOfPeople)
                .totalPrice(totalPrice)
                .contactName(request.getContactName())
                .contactPhone(request.getContactPhone())
                .contactEmail(request.getContactEmail())
                .status(BookingStatus.PENDING_PAYMENT)
                .build();

        for (BookingTravelerDto tDto : request.getTravelers()) {
            BookingTraveler traveler = BookingTraveler.builder()
                    .fullName(tDto.getFullName())
                    .gender(tDto.getGender())
                    .dateOfBirth(tDto.getDateOfBirth())
                    .phone(tDto.getPhone())
                    .travelerType(tDto.getTravelerType())
                    .build();
            booking.addTraveler(traveler);
        }

        Booking savedBooking = bookingRepository.save(booking);
        return mapToResponse(savedBooking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getUserBookingHistory(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserIdWithDetails(userId);
        return bookings.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findByIdWithDetails(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        // Verify the booking belongs to the user
        if (!booking.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("You are not authorized to cancel this booking");
        }

        // Only PENDING_PAYMENT bookings can be cancelled by user
        if (booking.getStatus() != BookingStatus.PENDING_PAYMENT) {
            throw new IllegalArgumentException("Only pending bookings can be cancelled");
        }

        // Release reserved slots
        TourDeparture departure = booking.getDeparture();
        departure.setAvailableSlot(departure.getAvailableSlot() + booking.getNumberOfPeople());
        tourDepartureRepository.save(departure);

        booking.setStatus(BookingStatus.CANCELLED);
        Booking savedBooking = bookingRepository.save(booking);
        return mapToResponse(savedBooking);
    }

    private BookingResponse mapToResponse(Booking booking) {
        List<BookingTravelerDto> travelers = booking.getTravelers().stream()
                .map(t -> BookingTravelerDto.builder()
                        .fullName(t.getFullName())
                        .gender(t.getGender())
                        .dateOfBirth(t.getDateOfBirth())
                        .phone(t.getPhone())
                        .travelerType(t.getTravelerType())
                        .build())
                .collect(Collectors.toList());

        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUser().getId())
                .departureId(booking.getDeparture().getId())
                .tourName(booking.getDeparture().getTour().getName())
                .departureDate(booking.getDeparture().getDepartureDate())
                .bookingDate(booking.getBookingDate())
                .numberOfPeople(booking.getNumberOfPeople())
                .totalPrice(booking.getTotalPrice())
                .contactName(booking.getContactName())
                .contactPhone(booking.getContactPhone())
                .contactEmail(booking.getContactEmail())
                .status(booking.getStatus())
                .travelers(travelers)
                .build();
    }
}
