package com.sunbooking.domain.booking.entity;

import com.sunbooking.domain.tour.entity.TourDeparture;
import com.sunbooking.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "bookings")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Booking extends BaseEntity {
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private TourDeparture tourDeparture;

    @OneToMany(mappedBy = "booking")
    private List<BookingTraveler> travelers; // Needed for slot calculation
}