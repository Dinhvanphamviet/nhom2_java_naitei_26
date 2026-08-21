package com.sunbooking.domain.payment.scheduler;

import com.sunbooking.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationCleanupScheduler {

    private final PaymentService paymentService;

    // Run every minute
    @Scheduled(cron = "0 * * * * *")
    public void cleanupExpiredReservations() {
        log.info("Starting cleanup for expired payment reservations...");
        paymentService.processExpiredPayments();
        log.info("Cleanup finished.");
    }
}