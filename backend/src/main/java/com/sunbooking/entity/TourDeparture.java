package com.sunbooking.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tour_departure")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "departure_id"))
})
public class TourDeparture extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "total_slot")
    private Integer totalSlot;

    @Column(name = "available_slot")
    private Integer availableSlot;

    @Enumerated(EnumType.STRING)
    private TourDepartureStatus status;

    public Tour getTour() { return tour; }
    public void setTour(Tour tour) { this.tour = tour; }
    public LocalDate getDepartureDate() { return departureDate; }
    public void setDepartureDate(LocalDate departureDate) { this.departureDate = departureDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getTotalSlot() { return totalSlot; }
    public void setTotalSlot(Integer totalSlot) { this.totalSlot = totalSlot; }
    public Integer getAvailableSlot() { return availableSlot; }
    public void setAvailableSlot(Integer availableSlot) { this.availableSlot = availableSlot; }
    public TourDepartureStatus getStatus() { return status; }
    public void setStatus(TourDepartureStatus status) { this.status = status; }
}