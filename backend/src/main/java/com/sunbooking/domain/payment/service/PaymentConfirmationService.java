package com.sunbooking.domain.payment.service;

import com.sunbooking.domain.booking.entity.Booking;
import com.sunbooking.domain.booking.entity.BookingStatus;
import com.sunbooking.domain.booking.repository.BookingRepository;
import com.sunbooking.domain.payment.dto.SePayWebhookRequest;
import com.sunbooking.domain.payment.entity.Payment;
import com.sunbooking.domain.payment.entity.PaymentStatus;
import com.sunbooking.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentConfirmationService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public void confirmPayment(SePayWebhookRequest request) {
        // Find payment by reference (content field in SePay)
        Payment payment = paymentRepository.findByTransactionReference(request.getContent())
                .orElseThrow(() -> new RuntimeException("Payment reference not found"));

        // Idempotency check: Only process if PENDING
        // Idempotency: Skip if already processed
        if (payment.getStatus() != PaymentStatus.PENDING) {
            return;
        }

        // Update payment details
        // 1. Update Payment status to SUCCESS
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setExternalTransactionId(request.getCode()); // Bank transaction code
        payment.setExternalTransactionId(request.getCode());
        payment.setPaidAt(LocalDateTime.now());

        paymentRepository.save(payment);

        // SUCCESS status here permanently secures the slots.
        // 2. Update linked Booking status to CONFIRMED
        Booking booking = payment.getBooking();
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        // Final CONFIRMED status secures reserved capacity permanently
    }
}