package com.sunbooking.domain.content.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tour_place")
public class TourPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_place_id")
    private Long id;

    @Column(name = "tour_id")
    private Long tourId;

    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "day_number")
    private Integer dayNumber;

    @Column(name = "visit_order")
    private Integer visitOrder;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public Long getTourId() {
        return tourId;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public Integer getVisitOrder() {
        return visitOrder;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}