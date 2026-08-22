package com.sunbooking.domain.review.entity;

import com.sunbooking.domain.booking.entity.Booking;
import com.sunbooking.global.common.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review")
@AttributeOverride(name = "id", column = @Column(name = "review_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", unique = true)
    private Booking booking;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "rating")
    private Integer rating;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ReviewImage> images = new ArrayList<>();

    public void addImage(ReviewImage image) {
        images.add(image);
        image.setReview(this);
    }

    public void removeImage(ReviewImage image) {
        images.remove(image);
        image.setReview(null);
    }
}
