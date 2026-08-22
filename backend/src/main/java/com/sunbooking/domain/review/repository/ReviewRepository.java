package com.sunbooking.domain.review.repository;

import com.sunbooking.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByBooking_Id(Long bookingId);

    @Query("SELECT DISTINCT r FROM Review r " +
            "JOIN FETCH r.booking b " +
            "JOIN FETCH b.user " +
            "JOIN FETCH b.departure d " +
            "JOIN FETCH d.tour " +
            "LEFT JOIN FETCH r.images " +
            "ORDER BY r.createdAt DESC")
    List<Review> findAllWithDetails();

    @Query("SELECT DISTINCT r FROM Review r " +
            "JOIN FETCH r.booking b " +
            "JOIN FETCH b.user " +
            "JOIN FETCH b.departure d " +
            "JOIN FETCH d.tour t " +
            "LEFT JOIN FETCH r.images " +
            "WHERE t.id = :tourId " +
            "ORDER BY r.createdAt DESC")
    List<Review> findByTourIdWithDetails(@Param("tourId") Long tourId);
}
