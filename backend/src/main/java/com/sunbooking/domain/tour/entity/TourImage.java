package com.sunbooking.domain.tour.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.sunbooking.global.common.BaseEntity;

@Entity
@Table(name = "tour_image")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "image_id"))
})
public class TourImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    public Tour getTour() { return tour; }
    public void setTour(Tour tour) { this.tour = tour; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}