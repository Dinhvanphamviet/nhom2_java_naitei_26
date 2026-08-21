package com.sunbooking.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SePayWebhookRequest {
    private Long id;              // SePay transaction ID
    private String gateway;       // Bank name (MBBank, Vietcombank...)

    @JsonProperty("transactionDate")
    private String transactionDate;

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("transferType")
    private String transferType;  // IN or OUT

    @JsonProperty("transferAmount")
    private BigDecimal transferAmount;

    private String content;       // This must match our transactionReference
    private String code;          // Bank transaction code
    private String referenceCode;
    private String description;
}