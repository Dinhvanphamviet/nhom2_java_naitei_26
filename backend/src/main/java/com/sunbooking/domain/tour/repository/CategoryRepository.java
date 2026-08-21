package com.sunbooking.domain.tour.repository;

import com.sunbooking.domain.tour.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}