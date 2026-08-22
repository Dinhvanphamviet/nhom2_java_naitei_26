package com.sunbooking.domain.booking.repository;

import com.sunbooking.domain.booking.dto.admin.AdminBookingFilter;
import com.sunbooking.domain.booking.entity.Booking;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BookingSpecification {

    public static Specification<Booking> filterBookings(AdminBookingFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getBookingId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filter.getBookingId()));
            }

            if (filter.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), filter.getStatus()));
            }

            if (filter.getDepartureId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("departure").get("id"), filter.getDepartureId()));
            }

            if (filter.getTourId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("departure").get("tour").get("id"), filter.getTourId()));
            }

            if (filter.getDepartureDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("departure").get("departureDate"), filter.getDepartureDate()));
            }

            if (StringUtils.hasText(filter.getSearchKeyword())) {
                String pattern = "%" + filter.getSearchKeyword().toLowerCase() + "%";
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("contactName")), pattern);
                Predicate phonePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("contactPhone")), pattern);
                Predicate emailPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("contactEmail")), pattern);
                predicates.add(criteriaBuilder.or(namePredicate, phonePredicate, emailPredicate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
