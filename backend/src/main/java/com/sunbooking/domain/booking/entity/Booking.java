package com.sunbooking.domain.booking.entity;

import com.sunbooking.domain.tour.entity.TourDeparture;
import com.sunbooking.domain.user.entity.User;
import com.sunbooking.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
@AttributeOverride(name = "id", column = @Column(name = "booking_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_id")
    private TourDeparture departure;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_email")
    private String contactEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookingStatus status;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BookingTraveler> travelers = new ArrayList<>();
    
    public void addTraveler(BookingTraveler traveler) {
        travelers.add(traveler);
        traveler.setBooking(this);
    }

    public void removeTraveler(BookingTraveler traveler) {
        travelers.remove(traveler);
        traveler.setBooking(null);
    }
}