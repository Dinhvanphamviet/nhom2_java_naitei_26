package com.sunbooking.domain.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunbooking.domain.payment.dto.SePayWebhookRequest;
import com.sunbooking.domain.payment.service.PaymentConfirmationService;
import com.sunbooking.domain.payment.service.PaymentWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments/webhook")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentWebhookService webhookService;
    private final PaymentConfirmationService confirmationService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestHeader("x-sepay-signature") String signature,
            @RequestBody String rawBody) {

        // 1. Verify security
        if (!webhookService.verifySignature(signature, rawBody)) {
            return ResponseEntity.status(401).body("Invalid signature");
        }

        try {
            // 2. Parse DTO from raw body
            SePayWebhookRequest request = objectMapper.readValue(rawBody, SePayWebhookRequest.class);

            // 3. Process business logic
            confirmationService.confirmPayment(request);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal error");
        }
    }
}