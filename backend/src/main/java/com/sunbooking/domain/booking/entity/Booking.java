package com.sunbooking.domain.booking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import java.math.BigDecimal;
import com.sunbooking.global.common.BaseEntity;

@Entity
@Table(name = "bookings")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Booking extends BaseEntity {
    private BigDecimal totalPrice;
}