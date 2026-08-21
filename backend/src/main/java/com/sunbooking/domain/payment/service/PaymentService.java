package com.sunbooking.domain.payment.service;

import com.sunbooking.config.SePayConfig;
import com.sunbooking.domain.booking.entity.Booking;
import com.sunbooking.domain.payment.dto.PaymentResponse;
import com.sunbooking.domain.payment.entity.Payment;
import com.sunbooking.domain.payment.repository.PaymentRepository;
import com.sunbooking.domain.tour.service.CapacityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SePayConfig sePayConfig;
    private final CapacityService capacityService;

    @Transactional
    public PaymentResponse createPayment(Booking booking) {
        // Reserve slots first to prevent overbooking
        capacityService.reserveCapacity(
                booking.getTourDeparture().getId(),
                booking.getTravelers().size()
        );

        // Generate unique reference for SePay
        String reference = sePayConfig.getQrPrefix() + booking.getId() +
                UUID.randomUUID().toString().substring(0, 4).toUpperCase();

        Payment payment = Payment.builder()
                .booking(booking)
                .amount(booking.getTotalPrice())
                .transactionReference(reference)
                .paymentMethod("VIETQR")
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        // SePay VietQR URL format
        String qrUrl = String.format("https://qr.sepay.vn/img?acc=%s&bank=%s&amount=%s&des=%s",
                sePayConfig.getBankAccount(), sePayConfig.getBankName(),
                savedPayment.getAmount().toPlainString(), reference);

        return mapToResponse(savedPayment, qrUrl);
    }

    private PaymentResponse mapToResponse(Payment payment, String qrUrl) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .bookingId(payment.getBooking().getId().toString())
                .amount(payment.getAmount())
                .transactionReference(payment.getTransactionReference())
                .status(payment.getStatus())
                .qrCodeUrl(qrUrl)
                .createdAt(payment.getCreatedAt())
                .expiredAt(payment.getExpiredAt()) // From @Transient method in Entity
                .build();
    }
}